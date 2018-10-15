import {Component, OnInit} from '@angular/core';

import {DataService} from "../../services/data.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  showPart;

  constructor(private dataService: DataService) {
  }

  ngOnInit() {
    this.dataService.loginStatus.subscribe(type => {
      this.showPart = type;
      console.log(type);
    });
  }

}
