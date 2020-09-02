import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoggedInGuard} from '../../api/guard/logged-in.guard';
import {LicensesComponent} from './components/licenses/licenses.component';
import {NewKeyComponent} from './components/new-key/new-key.component';
import {HomeComponent} from './home.component';

const routes: Routes = [
    {
        path: '',
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
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
})
export class HomeRoutingModule {
}
