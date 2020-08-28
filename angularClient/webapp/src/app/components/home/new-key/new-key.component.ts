import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, NgForm, Validators, } from '@angular/forms';
import {Router} from '@angular/router';
import {KeyService} from 'src/app/api/service/key/key.service';
import {FormStateMatcher} from '../../../shared/state-matchers/form.state-matcher';
import { MatDialogConfig, MatDialog } from '@angular/material/dialog';
import { ErrorModalComponent } from 'src/app/shared/modals/error-modal/error-modal.component';

/**
 * Компонент для создания нового ключа
 *
 * @author DSalikhov
 * @export
 */
@Component({
    selector: 'app-new-key',
    templateUrl: './new-key.component.html',
    styleUrls: ['./new-key.component.less'],
})
export class NewKeyComponent implements OnInit {
    /**
     * Форма создания ключа
     */
    newKeyForm: FormGroup;
    /**
     * boolean для спинера загрузки
     */
    isLoadingResults = false;
    /**
     * Валидатор
     */
    matcher = new FormStateMatcher();

    constructor(
        private formBuilder: FormBuilder,
        private router: Router,
        private modal: MatDialog,
        private keyService: KeyService
    ) {
    }

    ngOnInit(): void {
        this.newKeyForm = this.formBuilder.group({
            name: [null, Validators.required],
            expiration: [null, Validators.required],
            coresCount: [null, Validators.min(1)],
            usersCount: [null, Validators.min(1)],
            moduleFlags: this.formBuilder.group(
                {
                    platformOk: false,
                    EDS: false,
                    features: false,
                    archive: false,
                },
                Validators.required
            ),
            keyFileName: [null, Validators.required],
            comment: [null],
        });
    }

    /**
     * Производит создание нового ключа основывая на данных из формы
     *
     * @param form - форма с данными
     */
    createKey(form: NgForm) {
        this.isLoadingResults = true;

        const keyParams = this.newKeyForm.value;
        keyParams.moduleFlags = this.keyService.convertBooleanModuleFlagToByte(keyParams.moduleFlags);

        this.keyService.createNewKey(keyParams).subscribe(
            (res) => {
                this.isLoadingResults = false;
                this.router.navigate(['licenses']);
            },
            (err) => {
                const modalConfig = new MatDialogConfig();
                modalConfig.data = err;
                const modalDialog =  this.modal.open(ErrorModalComponent, modalConfig);
                this.isLoadingResults = false;
            }
        );
    }


}
