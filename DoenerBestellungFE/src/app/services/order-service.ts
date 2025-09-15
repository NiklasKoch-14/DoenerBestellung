import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Auth} from './auth';
import {Router} from '@angular/router';
import {Observable, tap} from 'rxjs';
import {Order} from '../orders/orders';

const apiUrl = 'http://localhost:8080/api/orders';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private http = inject(HttpClient);
  private authService = inject(Auth);
  private router = inject(Router);

  getOrder(): Observable<Order[]> {
    return this.http.get<Order[]>(apiUrl)
      .pipe(
        tap(_ => this.log('fetched orders'))
      );
  }

  private log(message: string) {
    console.log(message);
  }

}
