import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProductsComponent } from './components/products/products.component';
import { LoginComponent } from './components/auth/login/login.component';
import { RegisterComponent } from './components/auth/register/register.component';
import { AuthGuard } from './api/guard/auth.guard';
import { LicensesComponent } from './components/licenses/licenses.component';
import { NewKeyComponent } from './components/new-key/new-key.component';

const routes: Routes = [
  {
    path: 'products',
    canActivate: [AuthGuard],
    component: ProductsComponent,
  },
  {
    path: 'licenses',
    canActivate: [AuthGuard],
    component: LicensesComponent,
  },
  {
    path: 'newKey',
    canActivate: [AuthGuard],
    component: NewKeyComponent,
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'register',
    component: RegisterComponent,
  },
  {
    path: '',
    redirectTo: 'licenses',
    pathMatch: 'full',
  },
  {
    path: '**',
    redirectTo: '',
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
