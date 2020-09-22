import {Router} from '@angular/router';
import {EventEmitter, Injectable} from '@angular/core';
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
import {ModalService} from '@shared/service/modal/modal.service';
import {FormDataType} from '@api/license/form-data-type';
import {Page} from '@api/license/page';

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
        private modalService: ModalService,
    ) {
    }

    isUpdating$(): Observable<boolean> {
        return this.homeStore.isUpdating$();
    }

    getKeys(): Observable<Page<KeyGenerationParams>> {
        return this.homeStore.getKeys$();
    }

    getSelectedLicense() {
        return this.homeStore.getSelectedLicense$();
    }

    setSelectedLicense(license: LicenseType) {
        this.homeStore.setSelectedLicense(license);
    }

    /**
     * Производит создание нового ключа основывая на данных из формы
     *
     * @param $event
     */
    generate($event: FormDataType) {
        console.log('licenses createLicense');
        console.log($event);

        this.homeStore.setUpdating(true);

        const formData = new FormData();
        formData.append('keyMeta', new Blob([JSON.stringify($event.keyMeta)], {type: 'application/json'}));
        formData.append('activationFile', $event.activationKeyFile);

        this.keyService.createNewKey(formData, $event.type).subscribe(
            (message) => {
                this.logger.log(message);

                this.homeStore.setUpdating(false);
                this.refreshData();
            },
            (err) => {
                this.homeStore.setUpdating(false);
                const modalDialog = this.modalService.openErrorModal(err);
            },
            () => {
                this.homeStore.setUpdating(false);
            }
        );
    }

    openNewLicenseModal() {
        this.modalService.openNewLicenseModal(null, (data) => {
            this.generate(data);
        });
    }

    /**
     * Обновляет данные о ключах
     */
    refreshData(): void {
        this.homeStore.setUpdating(true);

        this.keyService.getKeys(this.homeStore.selectedLicenseValue$).subscribe(
            res => this.homeStore.setKeys(res),
            () => this.homeStore.setUpdating(false),
            () => this.homeStore.setUpdating(false)
        );
    }

    /**
     * Скачивает ключ
     *
     * @param keyFileId - id ключа
     * @param licenseType - тип лицензии
     */
    downloadKey(keyFileId: number, licenseType: LicenseType): void {
        this.keyService.downloadKey(keyFileId, licenseType).pipe(
            tap((res: HttpResponse<Blob>) => {
                const fileName = KeyUtils.getFileNameFromResponse(res);
                this.logger.log(fileName);
                saveAs(res.body, fileName);
            })
        ).subscribe();
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
                const modalDialog = this.modalService.openErrorModal(err);
            }
        );
    }
}
