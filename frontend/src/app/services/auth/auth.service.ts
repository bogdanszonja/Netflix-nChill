import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  loggedIn = new Subject<boolean>();

  constructor(private router: Router) { }

  sendToken(token: string) {
    sessionStorage.setItem('token', token.substring(7));
    this.loggedIn.next(true);
  }

  getToken() {
    return sessionStorage.getItem('token');
  }

  isLoggedIn() {
    return this.getToken() !== null;
  }

  logout() {
    sessionStorage.removeItem('token');
    this.loggedIn.next(false);
    this.router.navigate(['/']);
  }

}
