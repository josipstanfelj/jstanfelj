import { Component, OnInit } from '@angular/core';
import { WebshopService } from '../services/webshop.service';
import { User } from '../../backend/models/users.model';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  standalone: false,
  styleUrls: ['./users.component.css']
})
export class ListaComponent implements OnInit {
  users: User[] = [];
  errorMessage: string | null = null;

  constructor(private webshopService: WebshopService) {}

  ngOnInit(): void {
    this.webshopService.getAllUsers().subscribe({
      next: (data) => this.users = data,
      error: (err) => this.errorMessage = err.message
    });
  }
}
