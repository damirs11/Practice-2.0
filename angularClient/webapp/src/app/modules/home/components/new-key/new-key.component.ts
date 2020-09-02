import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, NgForm, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {FormStateMatcher} from '../../../../shared/state-matchers/form.state-matcher';
import {MatDialog} from '@angular/material/dialog';
import {HomeFacade} from '../../facade/home.facade';

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
     * Валидатор
     */
    matcher = new FormStateMatcher();

    constructor(
        private formBuilder: FormBuilder,
        private router: Router,
        private modal: MatDialog,
        private homeFacade: HomeFacade
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
     * @param formData - форма с данными
     */
    createKey(formData: NgForm) {
        this.homeFacade.createKey(formData);
    }
}
