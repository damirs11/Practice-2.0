import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../api/service/auth/auth.service';
import {Router} from '@angular/router';

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

    constructor(private authService: AuthService, private router: Router) {
    }

    ngOnInit(): void {
    }

    /**
     * Выход для кнопки выхода
     */
    logout() {
        this.authService.logout().subscribe(() => {
            this.router.navigate(['/auth/login']);
        });
    }
}
