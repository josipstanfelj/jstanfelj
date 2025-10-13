import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {ProfileComponent} from './profile/profile.component';
import {HomeComponent} from './home/home.component';
import {ProductsComponent} from './products/products.component';
import {InfoComponent} from './info/info.component';
import {MainComponent} from './main/main.component';
import {ProductDetailComponent} from './product-detail/product-detail.component';
import {AdministratorComponent} from './administrator/administrator.component';
import {OrdersComponent} from './orders/orders.component';
import {CartComponent} from './cart/cart.component';
import {ListaComponent} from './lista/lista.component';


const routes: Routes = [
  {
    path: '',
    component: MainComponent,
    children: [
      { path: 'home', component: HomeComponent },
      { path: 'products', component: ProductsComponent },
      { path: 'product/:id', component: ProductDetailComponent },
      { path: 'info', component: InfoComponent },
      { path: 'administrator', component: AdministratorComponent },
      { path: 'cart', component: CartComponent },
      { path: 'orders', component: OrdersComponent },
      { path: 'register', component: RegisterComponent },
      { path: 'login', component: LoginComponent },
      { path: 'profile', component: ProfileComponent },
      { path: 'users', component: ListaComponent },


    ]
  },
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
