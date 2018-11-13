import { Component, Input, OnInit } from '@angular/core';

import { Episode } from '../../../models/Episode';

@Component({
  selector: 'app-watched',
  templateUrl: './watched.component.html',
  styleUrls: ['./watched.component.css']
})
export class WatchedComponent implements OnInit {

  @Input() watchedEpisodes: Episode[];
  @Input() timeWasted;

  constructor() {
  }

  ngOnInit() {
  }

}
