import {Component, OnInit} from '@angular/core';
import {LoggerService} from '../../../../shared/service/logger/logger.service';
import {HomeFacade} from '../../shared/facade/home.facade';

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
})
export class LicensesComponent implements OnInit {
    constructor(
        private logger: LoggerService,
        private homeFacade: HomeFacade
    ) {
    }

    ngOnInit(): void {
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
     */
    downloadKey(keyFileId: number) {
        this.homeFacade.downloadKey(keyFileId).subscribe((_) => {
            this.logger.log('download' + keyFileId);
        });
    }
}
