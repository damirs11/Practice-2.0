import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {FormStateMatcher} from '@shared/state-matchers/form.state-matcher';
import KeyUtils from '@pages/home/shared/utils/keyUtils';
import {FormDataType} from '@api/license/form-data-type';
import {LicenseType} from '@api/license/enums/license-type';


@Component({
    selector: 'app-dummy-key',
    templateUrl: './dummy-key.component.html',
    styleUrls: ['./dummy-key.component.less']
})
export class DummyKeyComponent implements OnInit {
    /**
     * Определяет, что делать при submit формы
     */
    @Output() generate: EventEmitter<FormDataType> = new EventEmitter<FormDataType>();
    /**
     * Это Перевыпуск лицензии?
     */
    @Input() republish = false;
    /**
     * Форма создания ключа
     */
    newKeyForm: FormGroup;
    /**
     * Валидатор
     */
    matcher = new FormStateMatcher();

    minDate: Date = new Date();

    constructor(
        private formBuilder: FormBuilder,
    ) {
    }

    ngOnInit(): void {
        this.newKeyForm = this.formBuilder.group({
            keyMeta: this.formBuilder.group({
                id: [null],
                previousLicense: [null],
                dateOfIssue: [new Date(), Validators.required],
                dateOfExpiry: [null, Validators.required],
                properties: this.formBuilder.group({
                    organizationName: ['organizationName', Validators.required],
                    coresCount: [4, Validators.min(1)],
                    usersCount: [4, Validators.min(1)],
                    moduleFlags: this.formBuilder.group(
                        {
                            platformOk: true,
                            EDS: true,
                            features: false,
                            archive: false,
                        },
                        Validators.required
                    ),
                    keyFileName: ['keyFileName', Validators.required],
                    comment: ['comment'],
                }),
            }),
            files: this.formBuilder.group({
                activationKeyFile: [null]
            })
        });

        this.minDate.setDate(this.minDate.getDate() + 1);
    }

    /**
     * Производит создание нового ключа основывая на данных из формы
     *
     * @param formData - форма с данными
     */
    createKey(formData: any) {
        formData.keyMeta.properties.moduleFlags = KeyUtils.convertBooleanModuleFlagToByte(formData.keyMeta.properties.moduleFlags);
        formData.files.activationKeyFile = formData?.files?.activationKeyFile?._files[0] ?? null;
        formData.licenseType = LicenseType.DUMMY;
        console.log(formData);


        const formDataType = new FormDataType(formData);

        this.generate.emit(formDataType);
    }
}
