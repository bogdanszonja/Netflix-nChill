import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { UserService } from '../../services/user/user.service';
import { SeriesService } from '../../services/series/series.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  isLoggedIn = false;
  searchToggle = false;

  constructor(private userService: UserService,
              private seriesService: SeriesService,
              private router: Router) { }

  ngOnInit() {
    this.userService.loggedIn.subscribe(status => this.isLoggedIn = status);
  }

  showSearchField(): void {
    this.searchToggle = !this.searchToggle;
  }

  search(searchInput: string): void {
    this.seriesService.searchSeries(searchInput);
    this.router.navigate(['/search']);
    this.searchToggle = !this.searchToggle;
  }

  join(): void {
    this.userService.handleLogin('join');
  }

  login(): void {
    this.userService.handleLogin('login');
  }

  logout(): void {
    localStorage.removeItem('userId');
    this.userService.logoutUser();
    this.router.navigate(['/']);
  }

}
