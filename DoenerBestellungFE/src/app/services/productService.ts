import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {catchError, Observable, of, tap} from 'rxjs';
import {Product} from '../products/Product';
import {Auth} from './auth';
import {Router} from '@angular/router';

const apiUrl = 'http://localhost:8080/api/products';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private http = inject(HttpClient);
  private authService = inject(Auth);
  private router = inject(Router);

  getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(apiUrl)
      .pipe(
        tap(_ => this.log('fetched Products')),
        catchError(this.handleError('getProducts', [])),
      );
  }

  postDefaultOrder(defaultProducts: Product[]): Observable<any> {
    return this.http.post<any>(apiUrl + '/' + this.authService.getCurrentUsername() + '/defaultOrder', defaultProducts)
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      console.error(error); // log to console instead
      this.log(`${operation} failed: ${error.message}`);

      return of(result as T);
    };
  }

  private log(message: string) {
    console.log(message);
  }
}
