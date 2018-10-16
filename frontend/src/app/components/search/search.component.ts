import { Component, OnInit } from '@angular/core';
import { Series } from '../../models/Series';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  searchResult: Series[];
  selectedShow: Series;

  constructor() {}

  ngOnInit() {}

  selectShow(show: Series) {
    this.selectedShow = show;
  }
}

