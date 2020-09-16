import {Component} from '@angular/core';
import {HomeFacade} from './shared/service/facade/home.facade';
import {Observable} from 'rxjs';

/**
 * Корневой компонент для основного контента страницы
 *
 * @author DSalikhov
 * @export
 */
@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.less']
})
export class HomeComponent {
    isUpdating$: Observable<boolean>;

    constructor(private homeFacade: HomeFacade) {
        this.isUpdating$ = this.homeFacade.isUpdating$();
    }

    /**
     * Выход для кнопки выхода
     */
    logout(): void {
        this.homeFacade.logout();
    }
}
