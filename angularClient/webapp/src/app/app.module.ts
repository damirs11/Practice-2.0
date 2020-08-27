import {BrowserModule} from '@angular/platform-browser';
import {APP_INITIALIZER, NgModule} from '@angular/core';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import {MaterialFileInputModule} from 'ngx-material-file-input';

import {AppComponent} from './app.component';
import {AppRoutingModule} from './app-routing.module';
import {LoginComponent} from './components/auth/login/login.component';
import {RegisterComponent} from './components/auth/register/register.component';
import {HomeComponent} from './components/home/home.component';
import {AuthComponent} from './components/auth/auth.component';
import {AngularMaterialModule} from "./components/angular-material/angular-material.module";
import {LicensesComponent} from "./components/home/licenses/licenses.component";
import {NewKeyComponent} from "./components/home/new-key/new-key.component";
import {AuthService} from "./api/service/auth/auth.service";
import {ErrorModalComponent} from './shared/modals/error-modal/error-modal.component';
import {HttpErrorInterceptor} from "./api/interceptor/http-error-interceptor.service";
import { ForbiddenComponent } from './components/error/forbidden/forbidden.component';

@NgModule({
    declarations: [
        AppComponent,

        AuthComponent,
        LoginComponent,
        RegisterComponent,

        HomeComponent,
        LicensesComponent,
        NewKeyComponent,

        ErrorModalComponent,

        ForbiddenComponent,
    ],
    imports: [
        NgbModule,
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule,
        BrowserAnimationsModule,

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
            useFactory: (authService: AuthService) => () => authService.initService(),
            deps: [AuthService],
            multi: true
        },
    ],
    bootstrap: [AppComponent],
})
export class AppModule {
}
