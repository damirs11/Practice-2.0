import {Component, OnInit} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {environment} from '@env/environment';

/**
 * Корень приложения
 *
 * @author DSalikhov
 * @export
 */
@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.less'],
})
export class AppComponent implements OnInit{
    constructor(private translateService: TranslateService) {}

    ngOnInit(): void {
        this.translateService.use(environment.defaultLocale);
    }
}
