import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Cart, CartItem } from '../../backend/models/cart.model';
import { HttpClient } from '@angular/common/http';
import {Product} from '../../backend/models/products.model';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cart = new BehaviorSubject<Cart>(new Cart(''));
  cart$ = this.cart.asObservable();
  private userId: string = "";

  constructor(private http: HttpClient) {
  }


  addItemToCartProcedure(product: Product) {
    this.addToCart(product);
    if(this.userId) {
      this.saveCartToDatabase();
    }
  }

  addToCart(product: Product) {
    const currentCart = this.cart.value;
    currentCart.addItem(new CartItem(product._id, product.name, product.price, 1));
    this.updateCart(currentCart);
  }

  removeItemFromCartProcedure(productId: string) {
    const currentCart = this.cart.value;
    currentCart.removeItem(productId);
    this.updateCart(currentCart);
    if(this.userId) {
      this.saveCartToDatabase();
    }
  }

  decreaseFromCartProcedure(productId: string) {
    const currentCart = this.cart.value;
    currentCart.decreaseQuantity(productId);
    this.updateCart(currentCart);
    if(this.userId) {
      this.saveCartToDatabase();
    }
  }
  increaseFromCartProcedure(productId: string) {
    const currentCart = this.cart.value;
    currentCart.increaseQuantity(productId);
    this.updateCart(currentCart);
    if(this.userId) {
      this.saveCartToDatabase();
    }
  }

  loadCartFromDatabase() {
    this.http.get(`http://localhost:3000/api/cart/${this.userId}`).subscribe({
      next: (response: any) => {
        console.log("Košarica učitana:", response);
        if (response && response.cartItems) {
          this.cart.next(new Cart(this.userId, response.cartItems));
        } else {
          console.log("Košarica je prazna ili nije pronađena.");
        }
      },
      error: (err) => {
        console.error("Greška pri učitavanju košarice:", err);
      },
    });
  }



  loadUserIdFromLocalStorage() {
    const userId = localStorage.getItem('userId');
    console.log("User ID: " + userId);
    if (userId) {
      this.setUserId(userId);  // Ako postoji userId, postavi ga u CartService
    }
  }

  saveCartToDatabase() {
    if (this.userId) {
      this.http.post(`http://localhost:3000/api/cart/${this.userId}`, { cartItems: this.cart.value.cartItems }).subscribe({
        next: () => console.log("Košarica uspješno spremljena u bazu."),
        error: err => console.error("Greška pri spremanju u bazu:", err)
      });
    }
  }


  mergeCartFromDatabse(userId: string, cartItems: any[]) {
    this.http.post(`/api/cart/merge/${userId}`, { cartItems });
  }

  loadCartFromLocalStorage() {
    const savedCart = localStorage.getItem('cart');
    if (savedCart) {
      this.cart.next(Object.assign(new Cart(''), JSON.parse(savedCart)));
    }
  }

  clearCart() {
    const currentCart = new Cart(this.cart.value.userId);
    this.updateCart(currentCart);
  }

  private updateCart(cart: Cart) {
    this.cart.next(cart);
    localStorage.setItem('cart', JSON.stringify(cart));
  }

  getTotalPrice(): number {
    return this.cart.value.totalAmount;
  }

  setUserId(userId: string) {
    this.userId = userId;
  }
  removeUserId() {
    this.userId = "";
  }

  getCartItems(): CartItem[] {
    return this.cart.value.cartItems;
  }
  updateItemQuantity(productId: string, quantity: number) {
    const currentCart = this.cart.value;
    currentCart.updateQuantity(productId, quantity);
    this.updateCart(currentCart);
  }

  isEmpty(): boolean {
    const currentCart = this.cart.value;
    return currentCart.cartItems.length === 0;
  }

  /*

  removeItemFromDatabase(productId: string) {
    if (this.userId) {
      this.http.delete(`/api/cart/remove/${this.userId}/${productId}`).subscribe(() => {
        this.saveCartToDatabase();
      });
    }
  }
   */

}

