import { Injectable } from '@angular/core';
import { NavigationExtras, Router } from '@angular/router';
import { AppRoutes } from '../../models/app-routes';

@Injectable({
  providedIn: 'root',
})
export class NavigationService {
  constructor(private router: Router) {}
  push(route: string, params?: {}) {
    const navExtra: NavigationExtras = { state: params } ?? {};
    this.router.navigate([route], navExtra);
  }
  getExtras(): any {
    const data = this.router.getCurrentNavigation()?.extras?.state;
    if (typeof data === 'undefined') return null;
    return data;
  }
}
