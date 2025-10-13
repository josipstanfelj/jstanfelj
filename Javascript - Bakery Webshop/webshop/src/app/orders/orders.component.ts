import { Component, OnInit } from '@angular/core';
import { WebshopService } from '../services/webshop.service';
import { Order } from '../../backend/models/orders.model';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  standalone: false,
  styleUrls: ['./orders.component.css']
})

export class OrdersComponent implements OnInit {
  orders: Order[] = [];
  isAdmin: boolean = false;
  loading: boolean = true;
  error: string | null = null;

  constructor(private webshopService: WebshopService) {}

  ngOnInit(): void {
    this.webshopService.currentUser$.subscribe((user) => {
      if (user) {
        this.isAdmin = user.role === 'admin'; // Provjera role korisnika
        if (this.isAdmin) {
          this.getAllOrders();
        } else {
          this.getUserOrders(user._id);
        }
      }
    });
  }

  getAllOrders(): void {
    this.loading = true;
    this.webshopService.getAllOrders().subscribe({
      next: (orders) => {
        this.orders = orders;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Došlo je do greške pri dohvaćanju narudžbi.';
        console.error(err);
        this.loading = false;
      }
    });
  }

  getUserOrders(userId: string): void {
    this.loading = true;
    this.webshopService.getOrdersByUserId(userId).subscribe({
      next: (orders) => {
        this.orders = orders;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Došlo je do greške pri dohvaćanju narudžbi korisnika.';
        console.error(err);
        this.loading = false;
      }
    });
  }

  cancelOrder(orderId: string | undefined): void {
    this.webshopService.deleteOrder(orderId).subscribe({
      next: () => {
        this.orders = this.orders.filter(order => order._id !== orderId);
        alert('Narudžba je uspješno otkazana.');
      },
      error: (err) => {
        this.error = 'Došlo je do greške pri otkazivanju narudžbe.';
        console.error(err);
      }
    });
  }
}
