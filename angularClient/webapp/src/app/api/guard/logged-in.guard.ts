import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, CanActivateChild, Router, RouterStateSnapshot} from '@angular/router';
import {AuthService} from '../service/auth/auth.service';

/**
 *  LoggedInGuard служит для запрета входа на защищенные станицы,
 *  если пользователь не залогинен
 *
 * @author DSalikhov
 * @export
 */
@Injectable({
    providedIn: 'root',
})
export class LoggedInGuard implements CanActivateChild, CanActivate {
    constructor(private authService: AuthService, private router: Router) {
    }

    /**
     *  Логика для защиты детей роута
     *
     *  Проверят наличие статус пользователя,
     *  если вход в систему произведен, то пропускает,
     *  иначе кидает на страницу логина
     */
    canActivateChild(
        childRoute: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): boolean {
        if (this.authService.currentUserValue) {
            return true;
        }

        this.router.navigate(['/auth/login'], {queryParams: {returnUrl: state.url}});
        return false;
    }

    /**
     *  Логика для защиты корневого роута
     *
     *  Проверят наличие статус пользователя,
     *  если вход в систему произведен, то пропускает,
     *  иначе кидает на страницу логина
     */
    canActivate(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): boolean {
        const currentUser = this.authService.currentUserValue;
        if (currentUser) {
            return true;
        }

        this.router.navigate(['/auth/login'], {queryParams: {returnUrl: state.url}});
        return false;
    }
}
