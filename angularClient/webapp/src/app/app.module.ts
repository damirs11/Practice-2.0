import {BrowserModule} from '@angular/platform-browser';
import {APP_INITIALIZER, NgModule} from '@angular/core';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import {MaterialFileInputModule} from 'ngx-material-file-input';

import {AppComponent} from './app.component';
import {AppRoutingModule} from './app-routing.module';
import {ErrorModalComponent} from './shared/modals/error-modal/error-modal.component';
import {HttpErrorInterceptor} from './api/interceptor/http-error-interceptor.service';
import {AuthService} from './shared/service/auth/auth.service';
import {AngularMaterialModule} from './modules/angular-material/angular-material.module';
import {AuthModule} from './modules/auth/auth.module';
import {AuthStore} from './shared/store/auth.store';
import {tap} from 'rxjs/operators';
import {HomeModule} from './modules/home/home.module';

@NgModule({
    declarations: [
        AppComponent,

        ErrorModalComponent,
    ],
    imports: [
        NgbModule,
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule,
        BrowserAnimationsModule,

        HomeModule,
        AuthModule,
        AngularMaterialModule,

        MaterialFileInputModule,
    ],
    providers: [
        {
            provide: HTTP_INTERCEPTORS,
            useClass: HttpErrorInterceptor,
            multi: true,
        },
        {
            provide: APP_INITIALIZER,
            useFactory: (authService: AuthService, authStore: AuthStore) => () => {
                return authService.updateUserStatus().pipe(
                    tap((user) => {
                        authStore.setCurrentUser(user);
                    })
                ).toPromise();
            },
            deps: [AuthService, AuthStore],
            multi: true
        },
    ],
    bootstrap: [AppComponent],
})
export class AppModule {
}
