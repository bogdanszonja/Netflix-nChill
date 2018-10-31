import {Component, OnInit, ViewChild} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { UserService } from '../../services/user/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  showPart;

  constructor(private userService: UserService,
              private router: Router) { }

  ngOnInit() {
    this.userService.loginStatus.subscribe(type => {
      this.showPart = type;
      console.log(type);
    });
  }

  join(username: string, email: string, password: string, confirmPassword: string) {
    console.log(username, email, password, confirmPassword);
    this.userService.validateJoin(username, email, password, confirmPassword);
  }

  login(username: string, password: string) {
    this.userService.validateLogin(username, password);
    this.router.navigate(['/']);
  }

}
