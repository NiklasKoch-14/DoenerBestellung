import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot } from '@angular/router';
import { Auth } from '../auth';
import {inject} from '@angular/core';


export const authGuard: CanActivateFn = (route, state) => {
  let authService = inject(Auth);
  let router = inject(Router);

  if (authService.isLoggedIn) { return true; }

  // Store the attempted URL for redirecting
  authService.redirectUrl = state.url;

  // Navigate to the login page with extras
  router.navigate(['/login']);
  return false;
};
