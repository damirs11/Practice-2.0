import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {FormStateMatcher} from '@shared/state-matchers/form.state-matcher';
import KeyUtils from '@pages/home/shared/utils/keyUtils';
import {FormDataType} from '@api/license/form-data-type';
import {FormDataUzedoType} from "@api/license/form-data-uzedo-type";
import {MatChipInputEvent} from "@angular/material/chips";
import {COMMA, ENTER} from "@angular/cdk/keycodes";


@Component({
    selector: 'app-uzedo-key',
    templateUrl: './uzedo-key.component.html',
    styleUrls: ['./uzedo-key.component.less']
})
export class UzedoKeyComponent implements OnInit {
    /**
     * Определяет, что делать при submit-е формы
     */
    @Output() generate: EventEmitter<FormDataUzedoType> = new EventEmitter<FormDataUzedoType>();
    /**
     * Это Перевыпуск лицензии?
     */
    @Input() republish = false;
    /**
     * Форма создания ключа
     */
    uzedoKeyForm: FormGroup;
    /**
     * Валидатор
     */
    matcher = new FormStateMatcher();

    visible = true;
    removable = true;
    addOnBlur = true;
    isChipInputVisible = false;

    minDate: Date = new Date();

    constructor(
        private formBuilder: FormBuilder,
    ) {
    }

    ngOnInit(): void {
        this.uzedoKeyForm = this.formBuilder.group({
            keyMeta: this.formBuilder.group({
                id: [null],
                previousLicense: [null],
                version: ['v1'],
                dateOfIssue: [new Date(), Validators.required],
                dateOfExpiry: [null],
                issuedTo: ['Яндекс', Validators.required],
                issuedBy: ['Логика Бизнеса', Validators.required],
                licenseNumber: ['12'],
                organizationsList: [[], Validators.required],
                comment: ['комментарии']
            }),
            publicKey: [null, Validators.required]
        });

        this.minDate.setDate(this.minDate.getDate() + 1);
    }

    get organizationsList() {
        return this.uzedoKeyForm.get('keyMeta.organizationsList');
    }
    /**
     * Добавляет чипс в спискок
     */
    add($event: MatChipInputEvent): void {
        const value = $event.value;
        const input = $event.input;

        if (value.indexOf('_') > -1) {
           return;
        }

        // Add our innkpp
        if ((value || '').trim()) {
            this.organizationsList.setValue([...this.organizationsList.value, value.trim()]);
            this.organizationsList.updateValueAndValidity();
        }

        // Reset the input value
        if (input) {
            input.value = '';
        }
    }

    /**
     * Удаляет чипс из списка
     */
    remove(innkpp: string): void {
        const index = this.organizationsList.value.indexOf(innkpp);

        if (index >= 0) {
            this.organizationsList.value.splice(index, 1);
            this.organizationsList.updateValueAndValidity();
        }
    }

    /**
     * Производит создание нового ключа основывая на данных из формы
     *
     * @param formData - форма с данными
     */
    createKey(formData: any) {
        formData.keyMeta.organizationsList = formData.keyMeta.organizationsList.join(';');
        formData.publicKey = formData.publicKey._files[0] ?? null;

        console.log('Form generate');

        this.generate.emit(formData);
    }
}
