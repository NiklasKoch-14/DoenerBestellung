import {Component, inject, OnInit} from '@angular/core';
import {Auth} from '../services/auth';
import {Router} from '@angular/router';
import {Product} from '../products/Product';
import {OrderService} from '../services/order-service';
import {AsyncPipe, CurrencyPipe} from '@angular/common';
import {TableModule, TableRowCollapseEvent, TableRowExpandEvent} from 'primeng/table';
import {Observable} from 'rxjs';
import {Button} from 'primeng/button';

@Component({
  selector: 'app-orders',
  imports: [
    TableModule,
    AsyncPipe,
    Button,
  ],
  templateUrl: './orders.html',
  styleUrl: './orders.scss'
})
export class Orders implements OnInit {

  private authService = inject(Auth)
  private router = inject(Router)
  private orderService = inject(OrderService)

  orders: Observable<Order[]> | undefined;
  expandedRows = {};

  ngOnInit(): void {
    this.orders = this.orderService.getOrder()/*.subscribe({
      next: value => this.orders = value,
      error: err => console.error("errror bei order ", err)
    });*/
  }

  list() {
    console.log(this.orders);
  }

  onRowExpand(event: TableRowExpandEvent) {
    console.log(event);
  }

  onRowCollapse(event: TableRowCollapseEvent) {
    console.log(event);
  }

  products() {
    this.router.navigate(['products']);
  }
}

export class Order {
  username: string | undefined;
  products: Product[] | undefined;
}
