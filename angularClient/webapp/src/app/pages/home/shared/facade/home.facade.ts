import {Router} from '@angular/router';
import {Injectable} from '@angular/core';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {Observable} from 'rxjs';
import {AuthService} from '../../../../shared/service/auth/auth.service';
import {ErrorModalComponent} from '../../../../components/modals/error-modal/error-modal.component';
import {KeyParams} from '../../../../api/entity/license/keyParams';
import {KeyService} from '../service/key/key.service';
import {LoggerService} from '../../../../shared/service/logger/logger.service';
import {HomeStore} from '../store/home.store';
import {tap} from 'rxjs/operators';
import {HttpResponse} from '@angular/common/http';
import {saveAs} from 'file-saver';
import KeyUtils from '../utils/keyUtils';
import {AuthStore} from '../../../../shared/store/auth.store';

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

    getKeys(): Observable<KeyParams[]> {
        return this.homeStore.getKeys$();
    }

    /**
     * Обновляет данные о ключах
     */
    refreshData() {
        this.homeStore.setUpdating(true);
        this.keyService.getKeys().subscribe(
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
    downloadKey(keyFileId: number) {
        return this.keyService.downloadKey(keyFileId).pipe(
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
     * @param data - форма с данными
     */
    createKey(data: any) {
        this.homeStore.setUpdating(true);

        data.moduleFlags = KeyUtils.convertBooleanModuleFlagToByte(data.moduleFlags);
        data.activationKeyFile = data?.activationKeyFile?._files[0] ?? null;

        const formData = new FormData();
        for (const key in data) {
            if (data.hasOwnProperty(key) && data[key] !== null) {
                formData.append(key, data[key]);
            }
        }


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
