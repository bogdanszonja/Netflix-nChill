import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Subject } from 'rxjs/internal/Subject';
import { catchError, tap } from 'rxjs/operators';
import { of } from 'rxjs';

import { Series } from '../../models/Series';
import { Observable } from 'rxjs/internal/Observable';
import { Season } from '../../models/Season';
import { Episode } from '../../models/Episode';
import {User} from '../../models/User';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl = 'http://localhost:8080';

  loginStatus = new Subject<string>();

  constructor(private http: HttpClient) { }

  handleLogin(type: string) {
    console.log(this.loginStatus);
    this.loginStatus.next(type);
  }

  addWholeSeries(series: Series): Observable<Series> {
    return this.http.post<Series>(`${this.baseUrl}/user`, {'series': series.id}, httpOptions)
      .pipe(
        tap(_ => console.log(`Series added`)),
        catchError(this.handleError<Series>())
      );
  }

  addSingleSeason(season: Season): Observable<Season> {
    return this.http.post<Season>(`${this.baseUrl}/user`, {'season': season.id}, httpOptions)
      .pipe(
        tap(_ => console.log(`Season added`)),
        catchError(this.handleError<Season>())
      );
  }

  addSingleEpisode(episode: Episode): Observable<Episode> {
    return this.http.post<Episode>(`${this.baseUrl}/user`, {'episode': episode.id}, httpOptions)
      .pipe(
        tap(_ => console.log(`Episode added`)),
        catchError(this.handleError<Episode>())
      );
  }

  addToFavourites(series: Series): Observable<Series> {
    return this.http.post<Series>(`${this.baseUrl}/user`, {'favourite': series.id}, httpOptions)
      .pipe(
        tap(_ => console.log(`Series added to favourites`)),
        catchError(this.handleError<Series>())
      );
  }

  addToWatchlist(series: Series): Observable<Series> {
    return this.http.post<Series>(`${this.baseUrl}/user`, {'watchlist': series.id}, httpOptions)
      .pipe(
        tap(_ => console.log(`Series added to watchlist`)),
        catchError(this.handleError<Series>())
      );
  }

  validateJoin(username: string, email: string, password: string, confirmPassword: string): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/join`, {
      'username': username, 'email': email, 'password': password, 'confirmPassword': confirmPassword}, httpOptions)
      .pipe(
        tap(_ => console.log(`User join`)),
        catchError(this.handleError<any>())
      );
  }

  validateLogin(username: string, password: string): Observable<User> {
    return this.http.post<User>(`${this.baseUrl}/login`, {'username': username, 'password': password}, httpOptions)
      .pipe(
        tap(_ => console.log(`User login`)),
        catchError(this.handleError<User>())
      );
  }

  private handleError<T> (result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    };
  }

}
