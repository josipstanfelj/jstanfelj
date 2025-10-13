import { Component, OnInit } from '@angular/core';
import { CartService } from '../services/cart.service';
import { CartItem } from '../../backend/models/cart.model';
import { User } from '../../backend/models/users.model';
import { Router } from '@angular/router';
import { WebshopService } from '../services/webshop.service';
import { Order } from '../../backend/models/orders.model';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  standalone: false,
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  cartItems: CartItem[] = [];
  totalAmount: number = 0;

  userId: string = '';
  user: User = new User();
  successMessage: string = '';
  errorMessage: string = '';
  order: Order | null = null;

  constructor(
    private cartService: CartService,
    private router: Router,
    private service: WebshopService
  ) {}

  ngOnInit(): void {
    this.cartItems = this.cartService.getCartItems();
    this.calculateTotal();

    this.userId = localStorage.getItem('userId') || '';

    if (this.userId) {
      this.service.getUserById(this.userId).subscribe(user => {
        this.user = user;
      });
    } else {
      console.error('User ID not found in localStorage');
      this.router.navigate(['/login']);
    }
  }

  makePurchase(): void {
    if (!this.userId || this.cartItems.length === 0) {
      this.errorMessage = 'Nemate proizvoda u košarici ili ste odjavljeni.';
      return;
    }

    const products = this.cartItems.map(item => ({
      productId: item.productId,
      quantity: item.quantity
    }));

    const newOrder = new Order(
      this.userId,
      products,
      this.totalAmount,
      'pending'
    );

    this.service.addOrder(newOrder).subscribe(
      response => {
        this.successMessage = 'Narudžba je uspješno kreirana!';
        this.clearCart();
      },
      error => {
        console.error('Error creating order:', error);
        this.errorMessage = 'Došlo je do greške pri kreiranju narudžbe.';
      }
    );

    this.updateUser();
  }

  updateUser(): void {
    this.service.updateUserData(this.userId, this.user).subscribe(
      () => {
        this.successMessage = 'Podaci su uspješno ažurirani.';
      },
      error => {
        console.error('Error updating user data:', error);
        this.errorMessage = 'Došlo je do greške pri ažuriranju podataka.';
      }
    );
  }

  calculateTotal(): void {
    this.totalAmount = this.cartService.getTotalPrice();
  }

  increaseQuantity(item: CartItem): void {
    this.cartService.increaseFromCartProcedure(item.productId);
    this.cartItems = this.cartService.getCartItems();
    this.calculateTotal();
  }

  decreaseQuantity(item: CartItem): void {
    this.cartService.decreaseFromCartProcedure(item.productId);
    this.cartItems = this.cartService.getCartItems();
    this.calculateTotal();
  }

  removeFromCart(item: CartItem): void {
    this.cartService.removeItemFromCartProcedure(item.productId);
    this.cartItems = this.cartService.getCartItems();
    this.calculateTotal();
  }

  clearCart(): void {
    this.cartService.clearCart();
    this.cartItems = [];
    this.totalAmount = 0;
  }
}

