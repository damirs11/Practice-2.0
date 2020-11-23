import {Router} from '@angular/router';
import {Observable} from 'rxjs';
import {AuthService} from '@shared/service/auth/auth.service';
import {KeyGenerationParams} from '@api/license/key-generation-params';
import {KeyService} from '../key/key.service';
import {LoggerService} from '@shared/service/logger/logger.service';
import {HomeStore} from '../../store/home.store';
import {share, tap} from 'rxjs/operators';
import {HttpResponse} from '@angular/common/http';
import {saveAs} from 'file-saver';
import KeyUtils from '../../utils/keyUtils';
import {AuthStore} from '@shared/store/auth.store';
import {LicenseType} from '@api/license/enums/license-type';
import {ModalService} from '@shared/service/modal/modal.service';
import {Page} from '@api/license/page';
import {Injectable} from '@angular/core';
import {FormDataType} from '@api/license/form-data-type';

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

    getSelectedLicense(): Observable<LicenseType>  {
        return this.homeStore.getSelectedLicense$();
    }

    get selectedLicenseValue(): LicenseType  {
        return this.homeStore.selectedLicenseValue$;
    }

    setSelectedLicense(license: LicenseType): void {
        this.homeStore.setSelectedLicense(license);
    }

    /**
     * Производит создание нового ключа основывая на данных из формы
     *
     * @param $event
     * @param licenseType
     */
    generate($event: FormDataType): Observable<KeyGenerationParams> {
        this.homeStore.setUpdating(true);

        const formData = new FormData();
        formData.append('keyMeta', new Blob([JSON.stringify($event.keyMeta)], {type: 'application/json'}));
        for (const [key, value] of Object.entries($event.files)) {
            console.log(key, value);
            if (value !== null) {
                formData.append('files', value, key);
            }
        }

        const observable = this.keyService.createNewKey(formData, $event.licenseType);
        observable.subscribe(
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

        return observable;
    }

    openNewLicenseModal(keyGenerationParams?: KeyGenerationParams, licenseTypeByDefault?: LicenseType): void {
        const dialogRef = this.modalService.openNewLicenseModal((data) => {
            this.generate(data).subscribe(value => {
                    console.log('generate', value);
                    dialogRef.componentInstance.keyGenerationParams.next(value);
                });
        }, licenseTypeByDefault ?? null, keyGenerationParams ?? null);
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
