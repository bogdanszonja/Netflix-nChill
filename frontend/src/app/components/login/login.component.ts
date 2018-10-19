import { Component, OnInit } from '@angular/core';

import { UserService } from '../../services/user/user.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  showPart;

  constructor(private userService: UserService,
              private router: Router) {
  }

  ngOnInit() {
    this.userService.loginStatus.subscribe(type => {
      this.showPart = type;
      console.log(type);
    });
  }

  join(username: string, email: string, password: string, confirmPassword: string) {
    console.log(username, email, password, confirmPassword);
    this.userService.validateJoin(username, email, password, confirmPassword)
      .subscribe(answer => console.log(answer));
  }

  login(username: string, password: string) {
    this.router.navigateByUrl('/user-page');
    this.userService.validateLogin(username, password);
  }

}
