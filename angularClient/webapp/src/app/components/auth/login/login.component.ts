import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, NgForm, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from 'src/app/api/service/auth/auth.service';
import {FormStateMatcher} from '../../../shared/state-matchers/form.state-matcher';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {ErrorModalComponent} from '../../../shared/modals/error-modal/error-modal.component';
import {LoginRequest} from '../../../api/entity/dto/request/loginRequest.model';

/**
 * Компонент логина
 *
 * @author DSalikhov
 * @export
 */
@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.less'],
})
export class LoginComponent implements OnInit {
    /**
     * Форма логина
     */
    loginForm: FormGroup;
    /**
     * boolean для спинера загрузки
     */
    isLoadingResults = false;
    /**
     * По какой ссылки перейти после успешного логина
     */
    returnUrl: string;
    /**
     * Валидатор
     */
    matcher = new FormStateMatcher();

    constructor(
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private router: Router,
        private modal: MatDialog,
        private authService: AuthService
    ) {
    }

    ngOnInit(): void {
        this.loginForm = this.formBuilder.group({
            username: [null, Validators.required],
            password: [null, Validators.required],
        });

        this.returnUrl = this.route.snapshot.queryParams.returnUrl || '/';
    }

    /**
     * Производит логин основывая на данных из формы
     *
     * @param formData - форма с данными
     */
    login(formData: NgForm): void {
        this.isLoadingResults = true;
        this.authService.login(new LoginRequest(formData.username, formData.password)).subscribe(
            (res) => {
                this.isLoadingResults = false;
                this.router.navigate([this.returnUrl]);
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
