import {Component} from '@angular/core';
import {Observable} from 'rxjs';
import {AuthFacade} from './shared/services/auth.facade';

/**
 * Корневой компонент для безопасности
 *
 * @author DSalikhov
 * @export
 */
@Component({
    selector: 'app-auth',
    templateUrl: './auth.component.html',
    styleUrls: ['./auth.component.less']
})
export class AuthComponent {

    /**
     * boolean для спинера загрузки
     */
    isUpdating$: Observable<boolean>;

    constructor(private authFacade: AuthFacade) {
        this.isUpdating$ = authFacade.isUpdating$();
    }
}
