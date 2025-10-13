import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { BehaviorSubject, map, Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Category } from '../../backend/models/categories.model';
import { Product } from '../../backend/models/products.model';
import { Order } from '../../backend/models/orders.model';
import { User } from '../../backend/models/users.model';

@Injectable({
  providedIn: 'root'
})
export class WebshopService {
  private apiUrl = 'http://localhost:3000/api';

  constructor(private http: HttpClient) {}

  private userIdSubject = new BehaviorSubject<string | null>(localStorage.getItem('userId'));
  userId$ = this.userIdSubject.asObservable();

  private currentUserSubject = new BehaviorSubject<User | null>(null);
  currentUser$ = this.currentUserSubject.asObservable();

  public getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    let headers = new HttpHeaders();
    if (token) {
      headers = headers.set('Authorization', 'Bearer ' + token);
    }
    return headers;
  }

  setCurrentUser(user: User | null) {
    this.currentUserSubject.next(user);
  }

  setUserId(userId: string | null) {
    if (userId) {
      localStorage.setItem('userId', userId);
    } else {
      localStorage.removeItem('userId');
    }
    this.userIdSubject.next(userId);
  }

  updateUserData(userId: string, userData: User): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.put(`${this.apiUrl}/auth/${userId}`, userData, { headers }).pipe(
      catchError(this.handleError)
    );
  }

  getAllProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.apiUrl}/products`).pipe(
      catchError(this.handleError)
    );
  }

  getAllOrders(): Observable<Order[]> {
    return this.http.get<Order[]>(`${this.apiUrl}/orders`).pipe(
      catchError(this.handleError)
    );
  }

  getProductById(productId: string): Observable<Product> {
    return this.http.get<Product>(`${this.apiUrl}/products/${productId}`).pipe(
      catchError(this.handleError)
    );
  }

  addProduct(product: Product): Observable<Product> {
    return this.http.post<Product>(`${this.apiUrl}/products`, product).pipe(
      catchError(this.handleError)
    );
  }
  getAllCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(`${this.apiUrl}/categories`).pipe(
      catchError(this.handleError)
    );
  }

  addCategory(category: Category): Observable<Category> {
    return this.http.post<Category>(`${this.apiUrl}/categories`, category).pipe(
      catchError(this.handleError)
    );
  }

  getOrderById(orderId: string): Observable<Order> {
    return this.http.get<Order>(`${this.apiUrl}/orders/${orderId}`).pipe(
      catchError(this.handleError)
    );
  }

  addOrder(order: Order): Observable<Order> {
    return this.http.post<Order>(`${this.apiUrl}/orders`, order).pipe(
      catchError(this.handleError)
    );
  }

  getOrdersByUserId(userId: string): Observable<Order[]> {
    return this.http.get<Order[]>(`${this.apiUrl}/orders/user/${userId}`).pipe(
      catchError(this.handleError)
    );
  }

  deleteOrder(orderId: string | undefined): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/orders/${orderId}`).pipe(
      catchError(this.handleError)
    );
  }

  getUserById(userId: string | null): Observable<User> {
    const headers = this.getAuthHeaders();
    return this.http.get<User>(`${this.apiUrl}/auth/${userId}`, { headers }).pipe(
      catchError(this.handleError)
    );
  }

  getAllUsers(): Observable<User[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<{ users: User[] }>(`${this.apiUrl}/auth/`, { headers }).pipe(
      map(response => response.users),
      catchError(this.handleError)
    );
  }


  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'Došlo je do greške';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Greška: ${error.error.message}`;
    } else {
      errorMessage = `Server error (${error.status}): ${error.message}`;
    }
    console.error(errorMessage);
    return throwError(() => new Error(errorMessage));
  }
}
