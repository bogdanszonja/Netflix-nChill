import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  loggedIn = new Subject<boolean>();

  constructor(private myRoute: Router) { }

  sendToken(token: string) {
    localStorage.setItem('userId', token);
    this.loggedIn.next(true);
  }

  getToken() {
    return localStorage.getItem('userId');
  }

  isLoggedIn() {
    return this.getToken() !== null;
  }

  logout() {
    localStorage.removeItem('userId');
    this.loggedIn.next(false);
    this.myRoute.navigate(['/']);
  }

}
