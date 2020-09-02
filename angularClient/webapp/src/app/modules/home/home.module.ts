import {CommonModule} from '@angular/common';
import {HomeComponent} from './home.component';
import {LicensesComponent} from './components/licenses/licenses.component';
import {NewKeyComponent} from './components/new-key/new-key.component';
import {HomeRoutingModule} from './home-routing.module';
import {AngularMaterialModule} from '../angular-material/angular-material.module';
import {ReactiveFormsModule} from '@angular/forms';
import {NgModule} from '@angular/core';

@NgModule({
    declarations: [
        HomeComponent,
        LicensesComponent,
        NewKeyComponent
    ],
    imports: [
        CommonModule,
        HomeRoutingModule,
        AngularMaterialModule,
        ReactiveFormsModule,
    ]
})
export class HomeModule {
}
