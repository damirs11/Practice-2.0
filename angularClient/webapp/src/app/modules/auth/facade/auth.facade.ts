import {Injectable} from '@angular/core';
import {AuthService} from '../../../shared/service/auth/auth.service';
import {Observable} from 'rxjs';
import {ActivatedRoute, Router} from '@angular/router';
import {LoginRequest} from '../../../api/entity/dto/request/loginRequest.model';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog';
import {ErrorModalComponent} from '../../../shared/components/modals/error-modal/error-modal.component';
import {RegisterRequest} from '../../../api/entity/dto/request/registerRequest.model';
import {AuthStore} from '../../../shared/store/auth.store';

@Injectable({
    providedIn: 'root'
})
export class AuthFacade {
    /**
     * По какой ссылки перейти после успешного логина
     */
    returnUrl: string;

    constructor(
        private authService: AuthService,
        private authStore: AuthStore,
        private router: Router,
        private route: ActivatedRoute,
        private modal: MatDialog,
    ) {
        this.returnUrl = this.route.snapshot.queryParams.returnUrl || '/';
    }

    isUpdating$(): Observable<boolean> {
        return this.authStore.isUpdating$();
    }

    /**
     * Производит логин основывая на данных из формы
     *
     * @param username логин
     * @param password пароль
     */
    login(username: string, password: string): void {
        this.authStore.setUpdating(true);

        this.authService.login(new LoginRequest(username, password)).subscribe(
            (res) => {
                this.authService.updateUserStatus().subscribe((user) => {
                    this.authStore.setCurrentUser(user);
                    this.router.navigate([this.returnUrl]);
                });
            },
            (err) => {
                this.authStore.setUpdating(false);

                const modalConfig = new MatDialogConfig();
                modalConfig.data = err;

                const modalDialog = this.modal.open(ErrorModalComponent, modalConfig);
            },
            () => this.authStore.setUpdating(false)
        );
    }

    /**
     * Производит регистрацию основывая на данных из формы
     *
     * @param username логин
     * @param password пароль
     */
    registration(username: string, password: string): void {
        this.authStore.setUpdating(true);

        this.authService.registration(new RegisterRequest(username, password)).subscribe(
            (res) => this.router.navigate(['/auth/login']),
            (err) => {
                this.authStore.setUpdating(false);

                const modalConfig = new MatDialogConfig();
                modalConfig.data = err;

                const modalDialog = this.modal.open(ErrorModalComponent, modalConfig);
            },
            () => this.authStore.setUpdating(false)
        );
    }
}
