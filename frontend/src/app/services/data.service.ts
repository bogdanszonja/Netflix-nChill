import { Injectable } from '@angular/core';

import {Subject} from "rxjs/internal/Subject";

@Injectable({
  providedIn: 'root'
})
export class DataService {

  loginStatus = new Subject<string>();

  constructor() {
  }

  handleLogin(type: string) {
    console.log(this.loginStatus);
    this.loginStatus.next(type);
  }

}
