import { Component, Input, OnInit } from '@angular/core';
import { Series } from '../../../models/Series';

@Component({
  selector: 'app-watched',
  templateUrl: './watched.component.html',
  styleUrls: ['./watched.component.css']
})
export class WatchedComponent implements OnInit {

  @Input() watchedEpisodes: Series[];

  constructor() {
  }

  ngOnInit() {
  }

  calculateTimeWasted(): number {
    let result = 0;
    if (this.watchedEpisodes != null) {
      for (let series of this.watchedEpisodes) {
        for (let season of series.seasons) {
          for (let episode of season.episodes) {
            result += episode.runtime;
          }
        }
      }
    }
    console.log("time wasted: " + result);
    return result;
  }

}
