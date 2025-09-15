import { CanActivateFn, Router } from '@angular/router';
import { Auth } from '../services/auth';
import {inject} from '@angular/core';


export const authGuard: CanActivateFn = (route, state) => {
  let authService = inject(Auth);
  let router = inject(Router);

  if (authService.isLoggedIn && sessionStorage.getItem('token')) { return true; }

  // Store the attempted URL for redirecting
  authService.redirectUrl = state.url;

  // Navigate to the login page with extras
  router.navigate(['login']);
  return false;
};
