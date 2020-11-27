import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MaterialFileInputModule} from 'ngx-material-file-input';
import {AgGridModule} from 'ag-grid-angular';
import {NgxMaskModule} from 'ngx-mask';
import {HomeComponent} from './home.component';
import {HomeRoutingModule} from './home-routing.module';
import {AngularMaterialModule} from '../../angular-material/angular-material.module';
import {DummyKeyComponent} from '@pages/home/licenses/components/generate/components/dummy-key/dummy-key.component';
import {UzedoKeyComponent} from '@pages/home/licenses/components/generate/components/uzedo-key/uzedo-key.component';
import {LicensesComponent} from './licenses/licenses.component';
import {GenerateComponent} from './licenses/components/generate/generate.component';
import { PaginationToolbarComponent } from './licenses/components/pagination-toolbar/pagination-toolbar.component';
import { DropdownMenuComponent } from './licenses/components/dropdown-menu/dropdown-menu.component';

@NgModule({
    declarations: [
        HomeComponent,
        LicensesComponent,
        GenerateComponent,
        DummyKeyComponent,
        UzedoKeyComponent,
        PaginationToolbarComponent,
        DropdownMenuComponent,
    ],
    imports: [
        CommonModule,
        HomeRoutingModule,
        AngularMaterialModule,
        ReactiveFormsModule,
        MaterialFileInputModule,
        FormsModule,
        AgGridModule.forRoot(),
        NgxMaskModule.forRoot(),
    ]
})
export class HomeModule {
}
