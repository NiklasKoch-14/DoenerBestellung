import {
  ApplicationConfig, inject,
  provideBrowserGlobalErrorListeners,
  provideZonelessChangeDetection
} from '@angular/core';
import { provideRouter } from '@angular/router';
import {HttpEvent, HttpHandlerFn, HttpRequest, provideHttpClient, withInterceptors} from '@angular/common/http'
import { httpInterceptorProvider } from './interceptors';

import { routes } from './app.routes';
import {Observable} from 'rxjs';
import {Auth} from './auth';
import {authGuard} from './auth/auth-guard';

export const appConfig: ApplicationConfig = {
  providers: [
    provideHttpClient(withInterceptors([authInterceptor])),
    provideBrowserGlobalErrorListeners(),
    provideZonelessChangeDetection(),
    provideRouter(routes),
    httpInterceptorProvider,
  ]
};

export function authInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn): Observable<HttpEvent<unknown>> {

  const auth = inject(Auth);

  if(sessionStorage.getItem('token') && auth.isLoggedIn) {
    const authToken = sessionStorage.getItem('token');

    const authReq = req.clone({
      headers: req.headers.set('Authorization', authToken!)
    });

    return next(authReq);
  }
  return next(req);
}
