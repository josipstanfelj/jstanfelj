import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Product } from '../../backend/models/products.model';
import {CartService} from '../services/cart.service';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  standalone: false,
  styleUrls: ['./product-detail.component.css']
})
export class ProductDetailComponent implements OnInit {
  productId: string | null = null;
  product: Product | null = null;

  constructor(
    private route: ActivatedRoute,
    private http: HttpClient,
    private cartService : CartService
  ) {}

  ngOnInit(): void {
    this.productId = this.route.snapshot.paramMap.get('id');

    if (this.productId) {
      this.http.get<Product>(`http://localhost:3000/api/products/${this.productId}`).subscribe({
        next: (response) => {
          this.product = response;
        },
        error: (err) => {
          console.error('Greška pri dohvaćanju proizvoda:', err);
        }
      });
    }
  }

  insertToCart(product: Product) {
    this.cartService.addItemToCartProcedure(product);

  }
}

