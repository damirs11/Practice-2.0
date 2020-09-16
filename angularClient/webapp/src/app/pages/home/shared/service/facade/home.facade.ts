import {Router} from '@angular/router';
import {Injectable} from '@angular/core';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {Observable} from 'rxjs';
import {AuthService} from '@shared/service/auth/auth.service';
import {ErrorModalComponent} from '@components/modals/error-modal/error-modal.component';
import {KeyGenerationParams} from '@api/license/key-generation-params';
import {KeyService} from '../key/key.service';
import {LoggerService} from '@shared/service/logger/logger.service';
import {HomeStore} from '../../store/home.store';
import {tap} from 'rxjs/operators';
import {HttpResponse} from '@angular/common/http';
import {saveAs} from 'file-saver';
import KeyUtils from '../../utils/keyUtils';
import {AuthStore} from '@shared/store/auth.store';
import {LicenseType} from '@api/license/enums/license-type';

@Injectable({
    providedIn: 'any'
})
export class HomeFacade {

    constructor(
        private authService: AuthService,
        private keyService: KeyService,
        private homeStore: HomeStore,
        private authStore: AuthStore,
        private logger: LoggerService,
        private router: Router,
        private modal: MatDialog
    ) {
    }

    isUpdating$(): Observable<boolean> {
        return this.homeStore.isUpdating$();
    }

    getKeys(): Observable<KeyGenerationParams[]> {
        return this.homeStore.getKeys$();
    }

    getSelectedLicense(): LicenseType {
        return this.homeStore.getSelectedLicenseValue$();
    }

    setSelectedLicense(license: LicenseType) {
        this.homeStore.setSelectedLicense(license);
    }

    /**
     * Обновляет данные о ключах
     */
    refreshData(): void {
        this.homeStore.setUpdating(true);
        this.keyService.getKeys(this.homeStore.getSelectedLicenseValue$()).subscribe(
            res => this.homeStore.setKeys(res),
            () => this.homeStore.setUpdating(false),
            () => this.homeStore.setUpdating(false)
        );
    }

    /**
     * Скачивает ключ
     *
     * @param keyFileId - id ключа
     */
    downloadKey(keyFileId: number): void {
        this.keyService.downloadKey(keyFileId, this.homeStore.getSelectedLicenseValue$()).pipe(
            tap((res: HttpResponse<Blob>) => {
                const fileName = KeyUtils.getFileNameFromResponse(res);
                this.logger.log(fileName);
                saveAs(res.body, fileName);
            })
        );
    }

    /**
     * Производит создание нового ключа основывая на данных из формы
     *
     * @param licenseType - тип ключа
     * @param data - форма с данными
     * @param activationFile - файл активации
     */
    createKey(data: any, activationFile: File): void {
        this.homeStore.setUpdating(true);

        const formData = new FormData();
        formData.append('type', new Blob([JSON.stringify(this.homeStore.getSelectedLicenseValue$())], {type: 'application/json'}));
        formData.append('keyMeta', new Blob([JSON.stringify(data)], {type: 'application/json'}));
        console.log(JSON.stringify(data));
        formData.append('activationFile', activationFile);

        this.keyService.createNewKey(formData).subscribe(
            (message) => {
                this.logger.log(message);

                this.homeStore.setUpdating(false);
                this.router.navigate(['licenses']);
            },
            (err) => {
                this.homeStore.setUpdating(false);

                const modalConfig = new MatDialogConfig();
                modalConfig.data = err;

                const modalDialog = this.modal.open(ErrorModalComponent, modalConfig);
            },
            () => {
                this.homeStore.setUpdating(false);
            }
        );
    }

    /**
     * Выход с сайта
     * При ошибка показывает модалку с ошибкой
     */
    logout(): void {
        this.authService.logout().subscribe(
            () => {
                this.authStore.setCurrentUser(null);
                this.router.navigate(['/auth/login']);
            },
            (err) => {
                const modalConfig = new MatDialogConfig();
                modalConfig.data = err;

                const modalDialog = this.modal.open(ErrorModalComponent, modalConfig);
            }
        );
    }
}
