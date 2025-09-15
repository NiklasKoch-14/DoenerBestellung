import {Component, inject, OnInit, signal} from '@angular/core';
import {ProductService} from '../services/productService';
import {Product} from './Product';
import { Auth } from '../services/auth';
import { Router } from '@angular/router';
import { MatInputModule } from '@angular/material/input';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { CommonModule } from '@angular/common';
import {TableModule} from 'primeng/table';
import {Observable} from 'rxjs';
import {Button} from 'primeng/button';
import {Splitter} from 'primeng/splitter';

@Component({
  selector: 'app-products',
  imports: [
    CommonModule,
    MatInputModule,
    MatPaginatorModule,
    MatProgressSpinnerModule,
    MatSortModule,
    MatTableModule,
    MatIconModule,
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    TableModule,
    Button,
    Splitter
  ],
  templateUrl: './products.html',
  styleUrl: './products.scss'
})
export class Products implements OnInit {

  private productService = inject(ProductService)
  private authService = inject(Auth)
  private router = inject(Router)

  products: Observable<Product[]> | undefined;
  selectedProducts: Product[] = [];
  savedOrderId: number | undefined;

  ngOnInit() {
    this.products = this.productService.getProducts();
  }

  onRowSelectAdd(event: Product) {
    console.log("Product Selected ", event);
    this.selectedProducts.push(event!);
  }

  onRowUnselect(event: Product) {
    console.log("Product Unselected ", event);
    const index = this.selectedProducts.findIndex(product => product === event);

    if (index !== -1) {
      this.selectedProducts.splice(index, 1);
    }
  }

  order() {
    this.router.navigate(['orders']);
  }

  logout() {
    this.authService.logout()
  }

  saveDefaultProducts(selectedProducts: Product[]) {
    this.productService.postDefaultOrder(selectedProducts)
      .subscribe({
        next: (res) => {
          console.log('DefaultOrder Abgeschickt: ', selectedProducts)
          if(res.orderId) {
            this.savedOrderId = res.orderId;
          }
          this.router.navigate(['products'])
        }
      });
  }
}
