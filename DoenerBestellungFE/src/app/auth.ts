import {inject, Injectable} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, Observable, of, tap } from 'rxjs';
import {Router} from '@angular/router';

const apiUrl = 'http://localhost:8080/api/auth/';


@Injectable({
  providedIn: 'root'
})
export class Auth {

  isLoggedIn = false;
  redirectUrl: string | undefined;

  private http = inject(HttpClient);
  private router = inject(Router);

  login(data: any): Observable<any> {
    return this.http.post<any>(apiUrl + 'login', data)
      .pipe(
        tap(_ => {
          this.isLoggedIn = true;
          console.log("LoggedIn ", this.isLoggedIn);
        }),
        catchError(this.handleError('login', []))
      );
  }

  logout(){
    return this.http.post<any>(apiUrl + 'signout', undefined)
      .pipe(
        tap(_ => {
          this.isLoggedIn = false;
          console.log("LoggedIn ", this.isLoggedIn);
          sessionStorage.clear();
          console.log("removed token")
        }),
        catchError(this.handleError('logout', []))
      ).subscribe({
        next: (res) => {
          console.log("Logout ", res)
          this.router.navigate(['login'])
        },
        error: (err) => {
          console.log("error ", err)
        }
      })
  }

  register(data: any): Observable<any> {
    return this.http.post<any>(apiUrl + 'register', data)
      .pipe(
        tap(_ => this.log('login')),
        catchError(this.handleError('login', []))
      );
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
