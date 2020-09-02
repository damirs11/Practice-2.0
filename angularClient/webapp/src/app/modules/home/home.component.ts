import {Component, OnInit} from '@angular/core';
import {HomeFacade} from './facade/home.facade';
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
export class HomeComponent implements OnInit {
    isUpdating$: Observable<boolean>;

    constructor(private homeFacade: HomeFacade) {
        this.isUpdating$ = this.homeFacade.isUpdating$();
    }

    ngOnInit(): void {
    }

    /**
     * Выход для кнопки выхода
     */
    logout(): void {
        this.homeFacade.logout();
    }
}
