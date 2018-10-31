import { Component, Input, OnInit } from '@angular/core';
import { Series } from '../../../models/Series';

@Component({
  selector: 'app-watchlist',
  templateUrl: './watchlist.component.html',
  styleUrls: ['./watchlist.component.css']
})
export class WatchlistComponent implements OnInit {

  @Input() watchList: Series[];

  constructor() { }

  ngOnInit() {
  }

}
