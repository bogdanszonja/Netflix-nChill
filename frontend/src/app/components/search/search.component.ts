import { Component, OnInit } from '@angular/core';

import { Series } from '../../models/Series';
import { SeriesService } from '../../services/series/series.service';
import {Observable} from 'rxjs';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  searchResult: Series[] = [];
  selectedShow: Series;

  constructor(private seriesService: SeriesService) { }

  ngOnInit() {
    console.log('pina');
    this.seriesService.searchResult.subscribe(series => {
      this.searchResult = series;
      console.log(this.searchResult + 'eredmény');
      console.log(this.searchResult);
    });
  }

  selectShow(show: Series) {
    this.selectedShow = show;
  }

  // getSeries(): Observable<Series[]> {
  //   return this.seriesService.searchResult.subscribe(series => this.searchResult = series);
  // }

}

