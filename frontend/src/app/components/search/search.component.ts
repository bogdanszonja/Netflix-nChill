import { Component, OnInit } from '@angular/core';

import { Series } from '../../models/Series';
import { SeriesService } from '../../services/series/series.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  searchResult: Series[];
  selectedShow: Series;

  constructor(private seriesService: SeriesService) { }

  ngOnInit() {
    this.seriesService.getAllSeries()
      .subscribe(series => this.searchResult = series);
  }

  selectShow(show: Series) {
    this.selectedShow = show;
  }

}

