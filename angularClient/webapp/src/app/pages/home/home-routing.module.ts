import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoggedInGuard} from '@shared/guard/logged-in.guard';
import {LicensesComponent} from './licenses/licenses.component';
import {HomeComponent} from './home.component';
import {GenerateComponent} from '@pages/home/generate/generate.component';

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
                path: 'generate',
                component: GenerateComponent,
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
