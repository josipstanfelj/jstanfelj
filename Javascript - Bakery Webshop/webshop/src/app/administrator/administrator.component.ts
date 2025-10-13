import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { WebshopService } from '../services/webshop.service';
import {Category} from '../../backend/models/categories.model';

@Component({
  selector: 'app-administrator',
  templateUrl: './administrator.component.html',
  standalone: false,
  styleUrls: ['./administrator.component.css']
})
export class AdministratorComponent implements OnInit {
  productForm: FormGroup = new FormGroup({});
  categoryForm: FormGroup = new FormGroup({});
  productFormEdit: FormGroup = new FormGroup({});
  categoryFormEdit: FormGroup = new FormGroup({});
  categories: Category[] = [];
  products: any[] = [];
  successMessage: string = '';
  errorMessage: string = '';
  editingProduct: any = null;
  editingCategory: any = null;
  selectedFile: File | null = null;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router,
    private service: WebshopService
  ) { }

  ngOnInit(): void {
    this.productForm = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      price: ['', [Validators.required, Validators.min(0)]],
      stock: ['', [Validators.required, Validators.min(0)]],
      category: ['', Validators.required],
      image: ['', Validators.required]
    });
    this.productFormEdit = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      price: ['', [Validators.required, Validators.min(0)]],
      stock: ['', [Validators.required, Validators.min(0)]],
      category: ['', Validators.required],
      image: ['', Validators.required]
    });

    this.categoryForm = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required]
    });
    this.categoryFormEdit = this.fb.group({
      name: ['', Validators.required],
      description: ['', Validators.required]
    });

    this.http.get('http://localhost:3000/api/categories').subscribe((response: any) => {
      this.categories = response;
      console.log(response);
    });

    this.http.get('http://localhost:3000/api/products').subscribe((response: any) => {
      this.products = response;
    });
  }

  onSubmitProduct(): void {
    if (this.productForm.valid) {
      this.http.post('http://localhost:3000/api/products', this.productForm.value).subscribe(
        (response) => {
          this.successMessage = 'Proizvod uspješno dodan!';
          this.productForm.reset();
          this.refreshProducts();
        },
        (error) => {
          this.errorMessage = 'Greška pri dodavanju proizvoda';
        }
      );
    }
  }

  onSubmitCategory(): void {
    if (this.categoryForm.valid) {
      this.http.post('http://localhost:3000/api/categories', this.categoryForm.value).subscribe(
        (response) => {
          this.successMessage = 'Kategorija uspješno dodana!';
          this.categoryForm.reset();
          this.refreshCategories();
        },
        (error) => {
          this.errorMessage = 'Greška pri dodavanju kategorije';
        }
      );
    }
  }

  onEditProduct(product: any): void {
    this.editingProduct = { ...product };
    this.productFormEdit.patchValue(this.editingProduct);
  }

  onSaveProduct(): void {
    if (this.productFormEdit.valid) {
      this.http.put(`http://localhost:3000/api/products/${this.editingProduct._id}`, this.productFormEdit.value).subscribe(
        (response) => {
          this.successMessage = 'Proizvod uspješno ažuriran!';
          this.productFormEdit.reset();
          this.editingProduct = null;
          this.refreshProducts();
        },
        (error) => {
          this.errorMessage = 'Greška pri ažuriranju proizvoda';
        }
      );
    }
  }

  onDeleteProduct(productId: string): void {
    this.http.delete(`http://localhost:3000/api/products/${productId}`).subscribe(
      (response) => {
        this.successMessage = 'Proizvod uspješno obrisan!';
        this.refreshProducts();
      },
      (error) => {
        this.errorMessage = 'Greška pri brisanju proizvoda';
      }
    );
  }

  refreshProducts(): void {
    this.http.get('http://localhost:3000/api/products').subscribe((response: any) => {
      this.products = response;
    });
  }

  onEditCategory(category: any): void {
    this.editingCategory = { ...category };
    this.categoryFormEdit.patchValue(this.editingCategory);
  }

  onSaveCategory(): void {
    console.log("EDITIRANJE KATEGORIJE : " + this.editingCategory._id);
    if (this.categoryFormEdit.valid) {
      this.http.put(`http://localhost:3000/api/categories/${this.editingCategory._id}`, this.categoryFormEdit.value).subscribe(
        (response) => {
          this.successMessage = 'Kategorija uspješno ažurirana!';
          this.categoryFormEdit.reset();
          this.editingCategory = null;
          this.refreshCategories();
        },
        (error) => {
          this.errorMessage = 'Greška pri ažuriranju kategorije';
        }
      );
    }
  }

  onDeleteCategory(categoryId: string | undefined): void {
    if (!categoryId) {
      this.errorMessage = 'ID kategorije nije validan.';
      console.log(categoryId);
      return;
    }

    this.http.delete(`http://localhost:3000/api/categories/${categoryId}`).subscribe(
      (response) => {
        this.successMessage = 'Kategorija uspješno obrisana!';
        this.refreshCategories();
      },
      (error) => {
        this.errorMessage = 'Greška pri brisanju kategorije';
      }
    );
  }


  refreshCategories(): void {
    this.http.get('http://localhost:3000/api/categories').subscribe((response: any) => {
      this.categories = response;
    });
  }
}

