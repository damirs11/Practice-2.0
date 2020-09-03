import {Router} from '@angular/router';
import {Injectable} from '@angular/core';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {Observable} from 'rxjs';
import {AuthService} from '../../../../shared/service/auth/auth.service';
import {ErrorModalComponent} from '../../../../shared/components/modals/error-modal/error-modal.component';
import {KeyParamsInput} from '../../../../api/entity/keyParamsInput';
import {KeyService} from '../service/key/key.service';
import {LoggerService} from '../../../../shared/service/logger/logger.service';
import {HomeStore} from '../store/home.store';
import {tap} from 'rxjs/operators';
import {HttpResponse} from '@angular/common/http';
import {saveAs} from 'file-saver';
import KeyUtils from '../utils/keyUtils';

@Injectable({
    providedIn: 'any'
})
export class HomeFacade {

    constructor(
        private authService: AuthService,
        private keyService: KeyService,
        private homeStore: HomeStore,
        private logger: LoggerService,
        private router: Router,
        private modal: MatDialog
    ) {
    }

    isUpdating$(): Observable<boolean> {
        return this.homeStore.isUpdating$();
    }

    getKeys(): Observable<KeyParamsInput[]> {
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
     * @param formData - форма с данными
     */
    createKey(formData: any) {
        this.homeStore.setUpdating(true);

        formData.moduleFlags = KeyUtils.convertBooleanModuleFlagToByte(formData.moduleFlags);

        this.keyService.createNewKey(formData).subscribe(
            (res) => {
                this.homeStore.setUpdating(false);
                this.router.navigate(['licenses']);
            },
            (err) => {
                this.homeStore.setUpdating(false);

                const modalConfig = new MatDialogConfig();
                modalConfig.data = err;

                const modalDialog = this.modal.open(ErrorModalComponent, modalConfig);
            }
        );
    }

    /**
     * Выход с сайта
     * При ошибка показывает модалку с ошибкой
     */
    logout(): void {
        this.authService.logout().subscribe(
            () => this.router.navigate(['/auth/login']),
            (err) => {
                const modalConfig = new MatDialogConfig();
                modalConfig.data = err;

                const modalDialog = this.modal.open(ErrorModalComponent, modalConfig);
            },
        );
    }
}
