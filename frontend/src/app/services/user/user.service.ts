import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Subject } from 'rxjs/internal/Subject';
import { catchError, map, tap } from 'rxjs/operators';
import { of } from 'rxjs';

import { Series } from '../../models/Series';
import { Observable } from 'rxjs/internal/Observable';
import { Season } from '../../models/Season';
import { Episode } from '../../models/Episode';
import { User } from '../../models/User';
import { AuthService } from '../auth/auth.service';

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
  user = new Subject<User>();

  constructor(private http: HttpClient,
              private auth: AuthService) { }

  handleLogin(type: string) {
    console.log(this.loginStatus);
    this.loginStatus.next(type);
  }

  addWholeSeries(series: Series): Observable<Series> {
    return this.http.post<Series>(`${this.baseUrl}/user`,
      {'userId': localStorage.getItem('userId'), 'series': series.id}, httpOptions)
      .pipe(
        tap(_ => console.log(`Series added`)),
        catchError(this.handleError<Series>())
      );
  }

  addSingleSeason(season: Season): Observable<Season> {
    return this.http.post<Season>(`${this.baseUrl}/user`,
      {'userId': localStorage.getItem('userId'), 'season': season.id}, httpOptions)
      .pipe(
        tap(_ => console.log(`Season added`)),
        catchError(this.handleError<Season>())
      );
  }

  addSingleEpisode(episode: Episode): Observable<Episode> {
    return this.http.post<Episode>(`${this.baseUrl}/user`,
      {'userId': localStorage.getItem('userId'), 'episode': episode.id}, httpOptions)
      .pipe(
        tap(_ => console.log(`Episode added`)),
        catchError(this.handleError<Episode>())
      );
  }

  addToFavourites(series: Series): Observable<Series> {
    return this.http.post<Series>(`${this.baseUrl}/user`,
      {'userId': localStorage.getItem('userId'), 'favourite': series.id}, httpOptions)
      .pipe(
        tap(_ => console.log(`Series added to favourites`)),
        catchError(this.handleError<Series>())
      );
  }

  addToWatchlist(series: Series): Observable<Series> {
    return this.http.post<Series>(`${this.baseUrl}/user`,
      {'userId': localStorage.getItem('userId'), 'watchlist': series.id}, httpOptions)
      .pipe(
        tap(_ => console.log(`Series added to watchlist`)),
        catchError(this.handleError<Series>())
      );
  }

  validateJoin(username: string, email: string, password: string, confirmPassword: string): Observable<any> {
    if (!username.trim() || !email.trim() || !password.trim() || !confirmPassword.trim()) { return of(); }

    this.http.post<any>(`${this.baseUrl}/join`, {
      'username': username, 'email': email, 'password': password, 'confirmPassword': confirmPassword}, httpOptions)
      .pipe(
        tap(_ => console.log(`User join`)),
        catchError(this.handleError<any>())
      ).subscribe(response => {
        console.log(response);
    });
  }

  validateLogin(username: string, password: string): Observable<User> {
    if (!username.trim() || !password.trim()) { return of(); }

    this.http.post(`${this.baseUrl}/login`, {'username': username, 'password': password}, httpOptions)
      .pipe(
        tap(_ => console.log(`User login, should get back User`)),
        catchError(this.handleError<User>())
      ).subscribe(response => {
          this.auth.sendToken(response['data']['id']);
          this.user.next(response['data']);
    });
  }

  getUser(userId: number | null): Observable<User> {
    return this.http.get<User>(`${this.baseUrl}/user?userId=${userId}`)
      .pipe(
        tap(_ => console.log(`Getting User`)),
        map(response => {
          return response['data'];
        }),
        catchError(this.handleError<any>())
      );
  }

  private handleError<T> (result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    };
  }

  logoutUser() {
    this.auth.logout();
  }

}
