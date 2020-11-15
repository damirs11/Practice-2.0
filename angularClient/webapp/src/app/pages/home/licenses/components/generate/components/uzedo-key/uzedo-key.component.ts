import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {FormStateMatcher} from '@shared/state-matchers/form.state-matcher';
import {FormDataType} from '@api/license/form-data-type';
import {MatChipInputEvent} from '@angular/material/chips';
import {LicenseType} from '@api/license/enums/license-type';


@Component({
    selector: 'app-uzedo-key',
    templateUrl: './uzedo-key.component.html',
    styleUrls: ['./uzedo-key.component.less']
})
export class UzedoKeyComponent implements OnInit {
    /**
     * Определяет, что делать при submit-е формы
     */
    @Output() generate: EventEmitter<FormDataType> = new EventEmitter<FormDataType>();
    /**
     * Это Перевыпуск лицензии?
     */
    @Input() republish = false;
    /**
     * Форма создания ключа
     */
    form: FormGroup;
    /**
     * Валидатор
     */
    matcher = new FormStateMatcher();

    removable = true;
    addOnBlur = true;

    minDate: Date = new Date();

    constructor(
        private formBuilder: FormBuilder,
    ) {
    }

    ngOnInit(): void {
        this.form = this.formBuilder.group({
            keyMeta: this.formBuilder.group({
                id: [null],
                previousLicense: [null],
                dateOfIssue: [new Date(), Validators.required],
                dateOfExpiry: [],
                properties: this.formBuilder.group({
                    version: ['v1'],
                    issuedTo: ['Яндекс', Validators.required],
                    issuedBy: ['Логика Бизнеса', Validators.required],
                    licenseNumber: ['12', Validators.required],
                    organizationsList: [['1111111111:111111111'], Validators.required],
                    comment: ['комментарии'],
                }),
            }),
            files: this.formBuilder.group({
                publicKey: [null]
            })
        });

        this.minDate.setDate(this.minDate.getDate() + 1);
    }

    get organizationsList(): AbstractControl {
        return this.form.get('keyMeta.properties.organizationsList');
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
    createKey(formData: any): void {
        formData.keyMeta.properties.organizationsList = [...formData.keyMeta.properties.organizationsList].join(';');
        formData.files.publicKey = formData?.files?.publicKey?._files[0] ?? null;
        formData.licenseType = LicenseType.UZEDO;

        const formDataType = new FormDataType(formData);

        this.generate.emit(formDataType);
    }
}