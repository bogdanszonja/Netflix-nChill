import { Component, OnInit } from '@angular/core';

import { User } from '../../models/User';
import { UserService } from '../../services/user/user.service';

@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.css']
})
export class UserPageComponent implements OnInit {

  user: User;
  tabNum: number;

  constructor(private userService: UserService) { }

  ngOnInit() {
    console.log('User-page-constructor');
    this.getUser(localStorage.getItem('username'));
    this.tabNum = 1;
  }

  getUser(username: string): void {
    this.userService.getUser(username)
    .subscribe(user => {
      console.log(user);
      this.user = user;
    });
  }

  changeTab(tabNum: number): void {
    this.tabNum = tabNum;
  }

}
