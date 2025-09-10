import {Component, inject, OnInit} from '@angular/core';
import {ProductService} from '../productService';
import {Product} from './Product';
import { Auth } from '../auth';
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
import {Observable} from 'rxjs';

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
    MatFormFieldModule
  ],
  templateUrl: './products.html',
  styleUrl: './products.scss'
})
export class Products implements OnInit {

  private productService = inject(ProductService)
  private authService = inject(Auth)
  private router = inject(Router)

  data?: Observable<Product[]>;

  ngOnInit() {
    this.data = this.productService.getProducts();
  }

  logout() {
    this.authService.logout()
  }

}
