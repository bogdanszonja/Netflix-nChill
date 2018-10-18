import { Component, OnInit } from '@angular/core';
import { User } from '../../models/User';
import {UserService} from '../../services/user/user.service';

@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.css']
})
export class UserPageComponent implements OnInit {

  user: User;

  constructor(private userService: UserService) { }

  ngOnInit() {
    console.log('user-page-constructor');
    console.log(this.user);
    this.userService.user.subscribe(user => {
      this.user = user;
      console.log(this.user);
    });
  }

}
