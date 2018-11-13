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

  join(username, password, email, confirmPassword) {
    if (password.value !== confirmPassword.value) {
      this.toastr.error('Passwords do not match!', 'Pina');
      return;
    }
    console.log(username.value, password.value, email.value, confirmPassword.value);
    this.resetCredentials(username, password, email, confirmPassword);
    this.userService.validateJoin(username.value, password.value, email.value, confirmPassword.value);
  }

  login(username, password) {
    this.resetCredentials(username, password);
    this.userService.validateLogin(username.value, password.value);
    this.router.navigate(['/']);
  }

  handleLogin(type: string, username, password, email, confirmPassword) {
    this.userService.handleLogin(type);
    this.resetCredentials(username, password, email, confirmPassword);
  }

  handleJoin(type: string, username, password) {
    this.userService.handleLogin(type);
    this.resetCredentials(username, password);
  }

  resetCredentials(username, password, email?, confirmPassword?) {
    username.value = '';
    password.value = '';
    if (email && confirmPassword) {
      email.value = '';
      confirmPassword.value = '';
    }
  }

}
