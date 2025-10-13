import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { User } from '../../backend/models/users.model';
import { WebshopService } from '../services/webshop.service';

@Component({
  selector: 'app-profile',
  standalone: false,
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})

export class ProfileComponent implements OnInit {
  userId: string = '';
  user: User = new User();
  successMessage: string = '';
  errorMessage: string = '';

  constructor(private router: Router, private service: WebshopService) {}

  ngOnInit(): void {
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

  updateUser(): void {
    console.log(this.user);
    this.service.updateUserData(this.userId, this.user).subscribe(
      (response) => {
        this.successMessage = 'Podaci su uspješno ažurirani.';
      },
      (error) => {
        console.error('Error updating user data:', error);
        this.errorMessage = 'Došlo je do greške pri ažuriranju podataka.';
      }
    );
  }
}
