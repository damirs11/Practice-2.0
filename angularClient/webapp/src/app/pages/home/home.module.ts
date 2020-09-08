import {CommonModule} from '@angular/common';
import {HomeComponent} from './home.component';
import {LicensesComponent} from './licenses/licenses.component';
import {NewKeyComponent} from './new-key/new-key.component';
import {HomeRoutingModule} from './home-routing.module';
import {AngularMaterialModule} from '../../angular-material/angular-material.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NgModule} from '@angular/core';
import {MaterialFileInputModule} from 'ngx-material-file-input';

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
        MaterialFileInputModule,
        FormsModule,
    ]
})
export class HomeModule {
}
