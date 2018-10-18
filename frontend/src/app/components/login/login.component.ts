import { Component, OnInit } from '@angular/core';

import { UserService } from '../../services/user/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  showPart;

  constructor(private userService: UserService) {
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
    this.userService.validateLogin(username, password)
      .subscribe(user => console.log(user));
  }

}
