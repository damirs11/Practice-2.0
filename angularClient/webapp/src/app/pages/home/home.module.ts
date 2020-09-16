import {CommonModule} from '@angular/common';
import {HomeComponent} from './home.component';
import {LicensesComponent} from './licenses/licenses.component';
import {HomeRoutingModule} from './home-routing.module';
import {AngularMaterialModule} from '../../angular-material/angular-material.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NgModule} from '@angular/core';
import {MaterialFileInputModule} from 'ngx-material-file-input';
import {GenerateComponent} from './generate/generate.component';
import {DummyKeyComponent} from '@pages/components/dummy-key/dummy-key.component';
import {LicenseSelectorComponent} from '@pages/components/license-selector/license-selector.component';
import {DummyNoJarKeyComponent} from '@pages/components/dummy-no-jar-key/dummy-no-jar-key.component';

@NgModule({
    declarations: [
        HomeComponent,
        LicensesComponent,
        GenerateComponent,
        DummyKeyComponent,
        DummyNoJarKeyComponent,
        LicenseSelectorComponent,
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
