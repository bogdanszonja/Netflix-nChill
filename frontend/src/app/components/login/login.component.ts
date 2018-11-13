import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { UserService } from '../../services/user/user.service';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  showPart;

  constructor(private userService: UserService,
              private router: Router,
              private toastr: ToastrService) { }

  ngOnInit() {
    this.userService.loginStatus.subscribe(type => {
      this.showPart = type;
      console.log(type);
    });
  }

  join(username, email, password, confirmPassword, usernameLogin, passwordLogin) {
    if (password.value !== confirmPassword.value) {
      this.toastr.error('Passwords do not match!', 'Pina');
      return;
    }
    console.log(username.value, email.value, password.value, confirmPassword.value);
    this.resetCredentials(username, email, password, confirmPassword, usernameLogin, passwordLogin);
    this.userService.validateJoin(username.value, email.value, password.value, confirmPassword.value);
  }

  login(username, email, password, confirmPassword, usernameLogin, passwordLogin) {
    this.resetCredentials(username, email, password, confirmPassword, usernameLogin, passwordLogin);
    this.userService.validateLogin(usernameLogin.value, passwordLogin.value);
    this.router.navigate(['/']);
  }

  handleLogin(type: string) {
    this.userService.handleLogin(type);
  }

  resetCredentials(usernameJoin, passwordJoin, email, confirmPassword, usernameLogin, passwordLogin) {
    usernameJoin.value = '';
    passwordJoin.value = '';
    email.value = '';
    confirmPassword.value = '';
    usernameLogin.value = '';
    passwordLogin.value = '';
  }

}
