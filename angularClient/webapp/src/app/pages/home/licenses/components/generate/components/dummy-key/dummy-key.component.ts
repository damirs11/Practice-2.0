import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {FormStateMatcher} from '@shared/state-matchers/form.state-matcher';
import KeyUtils from '@pages/home/shared/utils/keyUtils';
import {FormDataType} from '@api/license/form-data-type';
import {LicenseType} from '@api/license/enums/license-type';
import {Observable} from 'rxjs';
import {KeyGenerationParams} from '@api/license/key-generation-params';


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
     * Определяет, что делать при очистке формы
     */
    @Output() clear: EventEmitter<void> = new EventEmitter<void>();
    /**
     * Это Перевыпуск лицензии?
     */
    @Input() keyGenerationParams: Observable<KeyGenerationParams>;
    /**
     * Форма создания ключа
     */
    form: FormGroup;
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
        this.form = this.formBuilder.group({
            keyMeta: this.formBuilder.group({
                id: [null],
                previousLicense: [null],
                dateOfIssue: [null],
                dateOfExpiry: [null, Validators.required],
                properties: this.formBuilder.group({
                    organizationName: ['organizationName', Validators.required],
                    coresCount: [4, Validators.min(1)],
                    usersCount: [4, Validators.min(1)],
                    moduleFlags: this.formBuilder.group(
                        {
                            platformOk: true,
                            EDS: false,
                            features: false,
                            archive: false,
                        },
                        Validators.required
                    ),
                    licenseFileName: ['licenseFileName'],
                    comment: ['comment'],
                }),
            }),
            files: this.formBuilder.group({
                activationKeyFile: [null]
            })
        });

        this.minDate.setDate(this.minDate.getDate() + 1);

        this.keyGenerationParams.subscribe((keyGenerationParams) => {
            if (keyGenerationParams !== null) {
                console.log(keyGenerationParams);

                keyGenerationParams.dateOfIssue = new Date(keyGenerationParams.dateOfIssue);
                keyGenerationParams.dateOfExpiry = keyGenerationParams?.dateOfExpiry ? new Date(keyGenerationParams.dateOfExpiry) : null;

                const tempFlags = KeyUtils.convertByteToBooleanModuleFlag(keyGenerationParams.properties['moduleFlags']);
                keyGenerationParams.properties['moduleFlags'] = {
                    platformOk: tempFlags[0],
                    EDS: tempFlags[1],
                    features: tempFlags[2],
                    archive: tempFlags[3],
                };

                this.form.controls.keyMeta.patchValue(keyGenerationParams);
                this.form.disable();
            } else {
                this.form.reset();
                this.form.enable();
            }
        });
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

        const formDataType = new FormDataType(formData);

        this.generate.emit(formDataType);
    }

    clearForm(): void {
        this.clear.emit();
    }
}
