import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {AuthFacade} from '../../shared/facade/auth.facade';

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
export class AuthComponent implements OnInit {

    /**
     * boolean для спинера загрузки
     */
    isUpdating$: Observable<boolean>;

    constructor(private authFacade: AuthFacade) {
        this.isUpdating$ = authFacade.isUpdating$();
    }

    ngOnInit(): void {
    }
}
