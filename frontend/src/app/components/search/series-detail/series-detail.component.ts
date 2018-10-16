import { Component, Input, OnInit } from '@angular/core';
import { Series } from "../../../models/Series";

@Component({
  selector: 'app-series-detail',
  templateUrl: './series-detail.component.html',
  styleUrls: ['./series-detail.component.css']
})
export class SeriesDetailComponent implements OnInit {

  @Input() series: Series;

  constructor() { }

  ngOnInit() {
  }

}
