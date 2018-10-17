import { Component, OnInit } from '@angular/core';

import { UserService } from '../../services/user/user.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  isLoggedIn = false;
  searchToggle = false;

  constructor(private userService: UserService) { }

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
  }

  login(): void {
    this.isLoggedIn = true;
    this.userService.handleLogin('login');
  }

  logout(): void {
    this.isLoggedIn = false;
  }
}
