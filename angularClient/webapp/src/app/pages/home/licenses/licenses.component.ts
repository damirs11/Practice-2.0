import {Component} from '@angular/core';
import {LoggerService} from '@shared/service/logger/logger.service';
import {HomeFacade} from '../shared/service/facade/home.facade';
import {LicenseType} from "@api/license/enums/license-type";

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
export class LicensesComponent {
    licenses = LicenseType;

    constructor(
        private logger: LoggerService,
        private homeFacade: HomeFacade
    ) {
    }

    get keys() {
        return this.homeFacade.getKeys();
    }

    get selectedLicense() {
        return this.homeFacade.getSelectedLicense();
    }

    onLicenseSelect(license: LicenseType) {
        this.homeFacade.setSelectedLicense(license);
        this.refreshData();
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
        this.homeFacade.downloadKey(keyFileId);
    }
}
