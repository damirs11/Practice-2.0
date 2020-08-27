import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, CanActivateChild, Router, RouterStateSnapshot,} from '@angular/router';
import {AuthService} from '../service/auth/auth.service';

@Injectable({
    providedIn: 'root',
})
export class LoggedInGuard implements CanActivateChild, CanActivate {
    constructor(private authService: AuthService, private router: Router) {
    }

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

    canActivate(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ):  boolean {
        const currentUser = this.authService.currentUserValue;
        if (currentUser) {
            return true;
        }

        this.router.navigate(['/auth/login'], {queryParams: {returnUrl: state.url}});
        return false;
    }
}
