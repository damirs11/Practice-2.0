import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './components/auth/login/login.component';
import {RegisterComponent} from './components/auth/register/register.component';
import {AuthComponent} from './components/auth/auth.component';
import {HomeComponent} from './components/home/home.component';
import {NewKeyComponent} from "./components/home/new-key/new-key.component";
import {LicensesComponent} from "./components/home/licenses/licenses.component";
import {LoggedInGuard} from "./api/guard/logged-in.guard";
import {ForbiddenComponent} from "./components/error/forbidden/forbidden.component";

const routes: Routes = [
    {
        path: 'home',
        component: HomeComponent,
        canActivate: [LoggedInGuard],
        canActivateChild: [LoggedInGuard],
        children: [
            {
                path: 'licenses',
                component: LicensesComponent,
            },
            {
                path: 'newKey',
                component: NewKeyComponent,
            },
        ]
    },
    {
        path: 'auth',
        component: AuthComponent,
        children: [
            {
                path: 'login',
                component: LoginComponent,
            },
            {
                path: 'registration',
                component: RegisterComponent,
            },
        ]
    },
    {
        path: 'error/403',
        component: ForbiddenComponent
    },
    {
        path: '',
        redirectTo: 'home/licenses',
        pathMatch: "full"
    },
    {
        path: '**',
        redirectTo: '404',
    },
];

@NgModule({
    imports: [RouterModule.forRoot(routes, )],
    exports: [RouterModule],
})
export class AppRoutingModule {
}
