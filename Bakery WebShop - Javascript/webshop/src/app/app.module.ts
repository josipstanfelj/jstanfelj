import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ProfileComponent } from './profile/profile.component';
import {HttpClientModule} from '@angular/common/http';
import { HomeComponent } from './home/home.component';
import { ProductsComponent } from './products/products.component';
import { InfoComponent } from './info/info.component';
import { MainComponent } from './main/main.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { MatBadgeModule } from '@angular/material/badge';
import { MatIconModule } from '@angular/material/icon';
import {MatIconButton} from '@angular/material/button';
import {MatMenu, MatMenuTrigger} from '@angular/material/menu';
import {MatList, MatListItem} from '@angular/material/list';
import {MatDivider} from '@angular/material/divider';
import { ProductDetailComponent } from './product-detail/product-detail.component';
import { AdministratorComponent } from './administrator/administrator.component';
import {OrdersComponent} from './orders/orders.component';
import { CartComponent } from './cart/cart.component';
import {PriceFormatPipe} from './pipes/price-format.pipe';
import { ListaComponent } from './lista/lista.component';




@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    ProfileComponent,
    HomeComponent,
    ProductsComponent,
    InfoComponent,
    MainComponent,
    ProductDetailComponent,
    AdministratorComponent,
    OrdersComponent,
    CartComponent,
    PriceFormatPipe,
    ListaComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    MatBadgeModule,
    MatIconModule,
    MatIconButton,
    MatMenuTrigger,
    MatListItem,
    MatList,
    MatDivider,
    MatMenu,
    ReactiveFormsModule
  ],
  providers: [
    provideAnimationsAsync()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
