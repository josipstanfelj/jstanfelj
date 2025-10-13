import { Component, OnInit } from '@angular/core';
import { WebshopService } from '../services/webshop.service';
import { Product } from '../../backend/models/products.model';
import {CartService} from '../services/cart.service';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css'],
  standalone: false
})
export class ProductsComponent implements OnInit {
  products: Product[] = [];
  filteredProducts: Product[] = [];
  categories: string[] = [];
  minPrice: number | null = null;
  maxPrice: number | null = null;
  selectedCategory: string = '';

  constructor(private webshopService: WebshopService, private cartService: CartService) { }

  ngOnInit() {
    this.webshopService.getAllProducts().subscribe((response) => {
      this.products = response;
    });
  }

  buyProduct(product: Product) {
    this.cartService.addItemToCartProcedure(product);

  }
}
