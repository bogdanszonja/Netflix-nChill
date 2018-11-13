import { Component, Input, OnInit } from '@angular/core';

import { Series } from '../../../models/Series';
import { Episode } from '../../../models/Episode';
import { Season } from '../../../models/Season';
import { UserService } from '../../../services/user/user.service';
import { User } from '../../../models/User';
import { SeriesService } from '../../../services/series/series.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-series-detail',
  templateUrl: './series-detail.component.html',
  styleUrls: ['./series-detail.component.css']
})
export class SeriesDetailComponent implements OnInit {

  series: Series;
  user: User;
  token: string = sessionStorage.getItem('token');
  showEpisodes = 0;
  hearted = false;
  isChecked: boolean;

  constructor(private userService: UserService,
              private seriesService: SeriesService,
              private toastr: ToastrService) { }

  ngOnInit() {
    this.userService.getUser(localStorage.getItem('username')).subscribe(response => {
      // if (response['data']) {
      console.log(response);
        this.user = response;
        console.log(this.user);
      // } else {
      //   console.log(response['error']);
      // }
    });
  }

  addWholeSeries(series: Series): void {
    console.log('all seasons added');
    this.userService.addWholeSeries(localStorage.getItem('username'), series).subscribe(response => {
      console.log(this.handleResponse(response));
      this.toastr.success(series.title + ' added to your list!');
      this.isChecked = true;
    });
  }

  addSingleSeason(season: Season): void {
    console.log('season added');
    this.userService.addSingleSeason(localStorage.getItem('username'), season).subscribe(response => {
      console.log(this.handleResponse(response));
      this.toastr.success('Season ' + season.seasonNumber + ' added to your list!');
    });
  }

  addSingleEpisode(episode: Episode): void {
    console.log('episode added');
    this.userService.addSingleEpisode(localStorage.getItem('username'), episode).subscribe(response => {
      console.log(this.handleResponse(response));
      this.toastr.success('Episode ' + episode.episodeNumber + ': ' + episode.title + ' added to your list!');
    });
  }

  addToFavourites(series: Series): void {
    console.log(this.user);
    console.log('added to favourites');
    this.userService.addToFavourites(localStorage.getItem('username'), series).subscribe(response => {
      console.log(this.handleResponse(response));
      this.toastr.success(series.title + ' added to your favourites!');
      this.hearted = !this.hearted;
    });
  }

  addToWatchlist(series: Series): void {
    console.log('added to watchlist');
    this.userService.addToWatchlist(localStorage.getItem('username'), series).subscribe(response => {
      console.log(this.handleResponse(response));
      this.toastr.success(series.title + ' added to your watchlist!');
    });
  }

  handleResponse(response) {
    // if (response['data']) {
      return response;
    // } else {
    //   return response['error'];
    // }
  }

  selectShow(seriesId: number) {
    this.seriesService.getSingleSeries(seriesId).subscribe(response => {
      console.log(response);
      this.series = response;
    });
  }

  toggleEpisodes(seasonId: number) {
    this.showEpisodes = seasonId;
  }

}
