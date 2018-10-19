import { Component, Input, OnInit } from '@angular/core';

import { Series } from '../../../models/Series';
import { Episode } from '../../../models/Episode';
import { Season } from '../../../models/Season';
import { UserService } from '../../../services/user/user.service';
import {User} from '../../../models/User';

@Component({
  selector: 'app-series-detail',
  templateUrl: './series-detail.component.html',
  styleUrls: ['./series-detail.component.css']
})
export class SeriesDetailComponent implements OnInit {

  @Input() series: Series;
  user: User;

  constructor(private userService: UserService) { }

  ngOnInit() {
    this.userService.user.subscribe(user => {
      this.user = user;
      console.log(this.user);
    });
  }

  addWholeSeries(userId: number, series: Series): void {
    this.userService.user.subscribe(user => userId = user.id);
    console.log(userId);
    console.log('all seasons added');
    this.userService.addWholeSeries(series)
      .subscribe(answer => console.log(answer));
  }

  addSingleSeason(season: Season): void {
    console.log('season added');
    this.userService.addSingleSeason(season).subscribe();
  }

  addSingleEpisode(episode: Episode): void {
    console.log('episode added');
    this.userService.addSingleEpisode(episode).subscribe();
  }

  addToFavourites(series: Series): void {
    console.log('added to favourites');
    this.userService.addToFavourites(series).subscribe();
  }

  addToWatchlist(series: Series): void {
    console.log('added to watchlist');
    this.userService.addToWatchlist(series).subscribe();
  }

}
