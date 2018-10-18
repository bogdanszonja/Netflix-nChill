import { Component, Input, OnInit } from '@angular/core';
import { Series } from '../../../models/Series';

@Component({
  selector: 'app-favourites',
  templateUrl: './favourites.component.html',
  styleUrls: ['./favourites.component.css']
})
export class FavouritesComponent implements OnInit {

  @Input() favourites: Series[];

  constructor() { }

  ngOnInit() {
  }

}
