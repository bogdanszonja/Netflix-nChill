import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';

import { UserService } from '../../services/user/user.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  isLoggedIn = false;
  searchToggle = false;

  constructor(private userService: UserService,
              private location: Location) {
  }

  ngOnInit() {
  }

  showSearchField(): void {
    this.searchToggle = !this.searchToggle;
  }

  search(searchInput: string): void {
    console.log(searchInput);
    this.searchToggle = !this.searchToggle;
  }

  join(): void {
    this.userService.handleLogin('join');
    this.location
  }

  login(): void {
    this.isLoggedIn = true;
    this.userService.handleLogin('login');
  }

  logout(): void {
    this.isLoggedIn = false;
  }
}
