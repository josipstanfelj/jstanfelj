import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { User } from '../../backend/models/users.model';
import {FormsModule} from '@angular/forms';
import {WebshopService} from '../services/webshop.service';
import {CartService} from '../services/cart.service';

@Component({
  selector: 'app-register',
  standalone: false,
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  username: string = '';
  password: string = '';
  confirmPassword: string = '';
  name: string = '';
  email: string = '';
  role: 'admin' | 'customer' | 'moderator' = 'customer';
  errorMessage: string = '';
  successMessage: string = '';
  errorMessageEmail: string = '';
  errorMessageUsername: string = '';
  errorMessagePassword: string = '';
  user :User | null = null;
  currentUserId: string = "";

  newUser: User = new User();

  constructor(private router: Router, private http: HttpClient, private service : WebshopService, private cartService : CartService) {}

  onRegister(): void {
    this.errorMessage = '';
    this.successMessage = '';
    this.errorMessageEmail = '';
    this.errorMessageUsername = '';
    this.errorMessagePassword = '';

    if (!this.username.trim() || !this.password.trim() ||
      !this.confirmPassword.trim() || !this.name.trim() || !this.email.trim()) {
      this.errorMessage = 'Sva polja moraju biti ispunjena!';
      return;
    }

    if (this.username.length < 4) {
      this.errorMessageUsername = 'Korisničko ime mora imati najmanje 4 znaka!';
    }
    if (!this.validateEmail(this.email)) {
      this.errorMessageEmail = 'Email nije valjan!';
    }
    if (this.password !== this.confirmPassword) {
      this.errorMessagePassword = 'Lozinke se ne podudaraju!';
    }
    if (this.errorMessagePassword || this.errorMessageEmail || this.errorMessageUsername) {
      return;
    }

    this.newUser = {
      _id: '',
      username: this.username,
      password: this.password,
      name: this.name,
      email: this.email,
      role: this.role
    };

    this.addUser();
  }

  validateEmail(email: string): boolean {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
  }

  goToLogin(): void {
    this.router.navigate(['/login']);
  }

  addUser() {
    this.http.post('http://localhost:3000/api/auth/register', this.newUser)
      .subscribe(
        (response: any) => {
          console.log('User successfully registered', response);

          this.service.setUserId(response.user.id);
          localStorage.setItem('token', response.token);
          this.cartService.setUserId(response.user.id);
          this.currentUserId = response.user.id;

          this.successMessage = 'Registracija uspješna!';

          this.username = '';
          this.password = '';
          this.confirmPassword = '';
          this.name = '';
          this.email = '';

          this.getUserDetails(this.currentUserId);
          setTimeout(() => {
            this.router.navigate(['/home']);
          }, 1500);
        },
        (error) => {
          console.error('Error during registration:', error);

          if (error.status === 400 && error.error.message) {
            this.errorMessage = error.error.message;
          } else {
            this.errorMessage = 'Došlo je do greške pri registraciji!';
          }
        }
      );
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


