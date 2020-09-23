import {CommonModule} from '@angular/common';
import {HomeComponent} from './home.component';
import {LicensesComponent} from './licenses/licenses.component';
import {HomeRoutingModule} from './home-routing.module';
import {AngularMaterialModule} from '../../angular-material/angular-material.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NgModule} from '@angular/core';
import {MaterialFileInputModule} from 'ngx-material-file-input';
import {GenerateComponent} from './generate/generate.component';
import {DummyKeyComponent} from '@pages/home/generate/components/dummy-key/dummy-key.component';
import {DummyNoJarKeyComponent} from '@pages/home/generate/components/dummy-no-jar-key/dummy-no-jar-key.component';
import {AgGridModule} from 'ag-grid-angular';
import { PaginationToolbarComponent } from './licenses/components/pagination-toolbar/pagination-toolbar.component';

@NgModule({
    declarations: [
        HomeComponent,
        LicensesComponent,
        GenerateComponent,
        DummyKeyComponent,
        DummyNoJarKeyComponent,
        PaginationToolbarComponent,
    ],
    imports: [
        CommonModule,
        HomeRoutingModule,
        AngularMaterialModule,
        ReactiveFormsModule,
        MaterialFileInputModule,
        FormsModule,
        AgGridModule.forRoot(),
    ]
})
export class HomeModule {
}
