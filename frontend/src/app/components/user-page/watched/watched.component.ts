import { Component, Input, OnInit } from '@angular/core';
import { Series } from '../../../models/Series';

@Component({
  selector: 'app-watched',
  templateUrl: './watched.component.html',
  styleUrls: ['./watched.component.css']
})
export class WatchedComponent implements OnInit {

  @Input() watchedEpisodes: Series[];

  constructor() { }

  ngOnInit() {
  }

}
