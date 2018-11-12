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

  join(username: string, email: string, password: string, confirmPassword: string) {
    if (password !== confirmPassword) {
      this.toastr.error('Passwords do not match!', 'Pina');
      return;
    }
    console.log(username, email, password, confirmPassword);
    this.userService.validateJoin(username, email, password, confirmPassword);
  }

  login(username: string, password: string) {
    this.userService.validateLogin(username, password);
    this.router.navigate(['/']);
  }

}
