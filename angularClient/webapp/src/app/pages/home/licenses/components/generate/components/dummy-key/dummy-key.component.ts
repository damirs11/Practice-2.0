import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {FormStateMatcher} from '@shared/state-matchers/form.state-matcher';
import KeyUtils from '@pages/home/shared/utils/keyUtils';
import {FormDataType} from '@api/license/form-data-type';


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
     * Форма создания ключа
     */
    newKeyForm: FormGroup;
    /**
     * Валидатор
     */
    matcher = new FormStateMatcher();

    constructor(
        private formBuilder: FormBuilder,
    ) {
    }

    ngOnInit(): void {
        this.newKeyForm = this.formBuilder.group({
            keyMeta: this.formBuilder.group({
                organization: ['organization', Validators.required],
                expiration: [new Date(), Validators.required],
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
                comment: ['comment']
            }),
            activationKeyFile: [null]
        });
        // this.keyMeta = this.newKeyForm.get('keyMeta').;
    }

    /**
     * Производит создание нового ключа основывая на данных из формы
     *
     * @param formData - форма с данными
     */
    createKey(formData: any) {
        formData.keyMeta.moduleFlags = KeyUtils.convertBooleanModuleFlagToByte(formData.keyMeta.moduleFlags);
        formData.activationKeyFile = formData?.activationKeyFile?._files[0] ?? null;

        console.log('Form generate');

        this.generate.emit(formData);
    }
}
