import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { WebshopService } from '../services/webshop.service';
import { CartService } from '../services/cart.service';
import {User} from '../../backend/models/users.model';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  message: string = '';
  successMessage: string = '';
  user :User | null = null;
  currentUserId: string = "";

  constructor(
    private router: Router,
    private http: HttpClient,
    private service: WebshopService,
    private cartService: CartService
  ) {}

  onSubmit() {
    this.message = '';
    this.currentUserId = "";

    if (!this.username || !this.password) {
      this.message = 'Molimo ispunite oba polja.';
      return;
    }

    this.http.post('http://localhost:3000/api/auth/login', { username: this.username, password: this.password })
      .subscribe(
        (response: any) => {
          localStorage.setItem('token', response.token);

          this.service.setUserId(response.user.id);
          this.currentUserId = response.user.id;
          console.log("OVO JE :" + this.currentUserId);

          this.cartService.setUserId(response.user.id);
          this.cartService.loadCartFromDatabase();

          this.successMessage = "Uspješna prijava!";

          this.getUserDetails(this.currentUserId);
        },
        (error) => {
          console.error('Greška pri prijavi:', error);
          this.message = error.error.message || 'Neuspješna prijava.';
        }
      );

    setTimeout(() => {
      this.router.navigate(['/home']);
    }, 1500);
  }

  getUserDetails(userId: string) {
    if (!userId) {
      console.error('Neispravan korisnički ID:', userId);
      return;
    }

    const headers = this.service.getAuthHeaders();

    this.http.get(`http://localhost:3000/api/auth/${userId}`, { headers })
      .subscribe(
        (userData: any) => {
          console.log('Podaci korisnika:', userData);
          this.service.setCurrentUser(userData);
        },
        (error) => {
          console.error('Greška pri dohvaćanju korisničkih podataka:', error);
        }
      );
  }

}






