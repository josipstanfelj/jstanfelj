import { Component } from '@angular/core';
import {Router, RouterLink, RouterOutlet} from '@angular/router';
import { WebshopService } from '../services/webshop.service';
import { User } from '../../backend/models/users.model';
import { CartService } from '../services/cart.service';
import {CartItem} from '../../backend/models/cart.model';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  standalone: false,
  styleUrl: './main.component.css'
})
export class MainComponent {
  title = 'forum';
  currentUserId: string | null = "";
  currentUser: User | null = null;
  korisnik: string = '';
  cartItems: CartItem[] = [];
  totalPrice: number = 0;

  constructor(
    private service: WebshopService,
    private router: Router,
    private cartService: CartService
  ) {}

  ngOnInit(): void {
    this.cartService.cart$.subscribe(cart => {
      this.cartItems = cart.cartItems;
      this.totalPrice = cart.totalAmount;
    });

    this.service.currentUser$.subscribe(user => {
      this.currentUser = user;
      this.korisnik = user?.username || '';
      console.log("Korisnik: " + this.currentUser);
      console.log(this.currentUser?.role);
    });

    this.service.userId$.subscribe(userId => {
      this.currentUserId = userId;
      if (this.currentUserId) {
        this.cartService.setUserId(this.currentUserId);
        console.log("UserID = " + this.currentUserId);
      } else {
        this.router.navigate(['/home']);
      }
    });

  }


  logout(): void {
    this.service.setUserId(null);
    this.service.setCurrentUser(null);
    localStorage.removeItem('token');
    this.clearCartOnLogout();
    this.currentUserId = null;
    this.currentUser = null;
    this.router.navigate(['/home']);
    this.cartService.removeUserId();
  }

  clearCartOnLogout(): void {
    this.cartService.clearCart();
  }

  get totalAmount(): number {
    return this.cartService.getTotalPrice();
  }

  viewCart() {
    alert('Otvaranje stranice košarice...');
  }

  checkout() {
    alert('Preusmjeravanje na blagajnu...');
  }

  removeFromCart(index: string): void {
    this.cartService.removeItemFromCartProcedure(index);
  }

  get cartItemCount(): number {
    return this.cartItems.reduce((total, item) => total + item.quantity, 0);
  }


  increaseQuantity(productId: string) {
    this.cartService.increaseFromCartProcedure(productId);
  }

  decreaseQuantity(productId: string) {
    this.cartService.decreaseFromCartProcedure(productId);
  }
  cartMenuVisible: boolean = false;

  toggleCartMenu(): void {
    this.cartMenuVisible = !this.cartMenuVisible;

  }


}

