import {Component} from '@angular/core';
import {LoggerService} from '@shared/service/logger/logger.service';
import {HomeFacade} from '../shared/service/facade/home.facade';
import {LicenseType} from '@api/license/enums/license-type';
import {ColDef, GridApi, GridOptions, RowClickedEvent} from 'ag-grid-community';
import {DatePipe} from '@angular/common';
import {PageSettings} from '@api/license/pageSettings';

/**
 * Компонент для логина
 *
 * @author DSalikhov
 * @export
 */
@Component({
    selector: 'app-licenses',
    templateUrl: './licenses.component.html',
    styleUrls: ['./licenses.component.less'],
    providers: [DatePipe]
})
export class LicensesComponent {
    licenses = LicenseType;
    gridOptions: GridOptions;

    columnDefs: ColDef[];
    defaultColDef;
    gridApi: GridApi;

    constructor(
        private logger: LoggerService,
        private homeFacade: HomeFacade,
        private date: DatePipe
    ) {
        this.defaultColDef = {
            editable: false,
            sortable: true,
            flex: 1,
            minWidth: 100,
            filter: true,
            resizable: true,
            lockVisible: true,
        };

        this.homeFacade.getSelectedLicense().subscribe((license) => {
            switch (license) {
                case LicenseType.DUMMY: {
                    this.columnDefs = [];
                    break;
                }
                case LicenseType.UZEDO: {
                    this.columnDefs = [
                        {
                            headerName: 'UUID',
                            field: 'id',
                        },
                        {
                            headerName: 'Версия',
                            field: 'properties.version',
                        },
                        {
                            headerName: 'Выдан кому',
                            field: 'properties.issuedTo',
                        },
                        {
                            headerName: 'Выдан кем',
                            field: 'properties.issuedBy',
                        },
                        {
                            headerName: 'Номер лицензии',
                            field: 'properties.licenseNumber',
                        },
                        {
                            headerName: 'Организации',
                            field: 'properties.organizationsList',
                        },
                        {
                            headerName: 'Комментарий',
                            field: 'properties.comment',
                        },
                    ];
                    break;
                }
                default: {
                    this.columnDefs = [
                        {
                            headerName: 'UUID',
                            field: 'id',
                        },
                        {
                            headerName: 'Тип',
                            field: 'licenseType',
                        },
                        {
                            headerName: 'Дата создания',
                            field: 'dateOfIssue',
                            valueFormatter: (params) => this.date.transform(params.value, 'dd.MM.yyyy'),
                        },
                        {
                            headerName: 'Дата окончания',
                            field: 'dateOfExpiry',
                            valueFormatter: (params) => this.date.transform(params.value, 'dd.MM.yyyy'),
                            initialSort: 'desc'
                        },
                    ];
                }
            }
        });

        PageSettings.size = 2;

        this.gridOptions = {
            suppressRowDeselection: true,
            pagination: false,
            paginationPageSize: PageSettings.size,
            onGridReady: (params) => {
                this.gridApi = params.api;

                this.keys.subscribe(rowData => {
                        this.gridApi.setRowData(rowData.content);
                    }
                );

                window.addEventListener('onresize', () => {
                    this.gridApi.sizeColumnsToFit();
                });
            },
            getRowNodeId(data) {
                return data.id;
            },
            onRowClicked: (event: RowClickedEvent) =>
                this.downloadKey(event.data.id, event.data.licenseType)
        };

        this.refreshData();
    }

    get keys() {
        return this.homeFacade.getKeys();
    }

    /**
     * Обновляет данные о ключах
     */
    refreshData() {
        this.homeFacade.refreshData();
    }

    /**
     * Скачивает ключ
     *
     * @param keyFileId - id ключа
     * @param licenseType - тип лицензии
     */
    downloadKey(keyFileId: number, licenseType: LicenseType) {
        console.log(keyFileId, licenseType);
        this.homeFacade.downloadKey(keyFileId, licenseType);
    }

    /**
     * Открывает диалоговое окно с генерацией лицензии
     */
    createLicense() {
        this.homeFacade.openNewLicenseModal();
    }

    moduleFlagsToLabel(moduleFlags: number): string {
        // tslint:disable-next-line:no-bitwise
        const flags = (moduleFlags >> 4).toString(2).split('');

        for (const flag in flags) {
            if (flags[flag] !== '1') {
                continue;
            }
            switch (flag) {
                case '0':
                    flags[flag] = 'Платформа';
                    break;
                case '1':
                    flags[flag] = 'СЭД';
                    break;
                case '2':
                    flags[flag] = 'фичи';
                    break;
                case '3':
                    flags[flag] = 'архив';
                    break;
            }
        }

        return flags.filter(flag => flag !== '0').toString();
    }
}
