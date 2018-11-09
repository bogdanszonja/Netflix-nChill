import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpResponse} from '@angular/common/http';

import { Subject } from 'rxjs/internal/Subject';
import { catchError, tap } from 'rxjs/operators';
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
    return this.http.post<any>(`${this.baseUrl}/user`,
      {'userId': localStorage.getItem('userId'), 'series': series.id}, httpOptions)
      .pipe(
        tap(_ => console.log(`Series added`)),
        catchError(response => this.handleError(response, response.error['error']))
      );
  }

  addSingleSeason(season: Season): Observable<Season> {
    return this.http.post<any>(`${this.baseUrl}/user`,
      {'userId': localStorage.getItem('userId'), 'season': season.id}, httpOptions)
      .pipe(
        tap(_ => console.log(`Season added`)),
        catchError(response => this.handleError(response, response.error['error']))
      );
  }

  addSingleEpisode(episode: Episode): Observable<Episode> {
    return this.http.post<any>(`${this.baseUrl}/user`,
      {'userId': localStorage.getItem('userId'), 'episode': episode.id}, httpOptions)
      .pipe(
        tap(_ => console.log(`Episode added`)),
        catchError(response => this.handleError(response, response.error['error']))
      );
  }

  addToFavourites(series: Series): Observable<Series> {
    return this.http.post<any>(`${this.baseUrl}/user`,
      {'userId': localStorage.getItem('userId'), 'favourite': series.id}, httpOptions)
      .pipe(
        tap(_ => console.log(`Series added to favourites`)),
        catchError(response => this.handleError(response, response.error['error']))
      );
  }

  addToWatchlist(series: Series): Observable<Series> {
    return this.http.post<any>(`${this.baseUrl}/user`,
      {'userId': localStorage.getItem('userId'), 'watchlist': series.id}, httpOptions)
      .pipe(
        tap(_ => console.log(`Series added to watchlist`)),
        catchError(response => this.handleError(response, response.error['error']))
      );
  }

  validateJoin(username: string, email: string, password: string, confirmPassword: string): Observable<any> {
    if (!username.trim() || !email.trim() || !password.trim() || !confirmPassword.trim()) { return of(); }

    this.http.post<any>(`${this.baseUrl}/users/join`, {
      'username': username, 'email': email, 'password': password, 'confirmPassword': confirmPassword}, httpOptions)
      .pipe(
        tap(_ => console.log(`User join`)),
        catchError(response => this.handleError(response, response.error['error']))
      ).subscribe(response => {
        console.log(response);
    });
  }

  validateLogin(username: string, password: string): Observable<User> {
    if (!username.trim() || !password.trim()) { return of(); }

    this.http.post(`${this.baseUrl}/login`, {'username': username, 'password': password}, {
      headers: new HttpHeaders({'Content-Type': 'application/json'}),
      observe: 'response'
    })
      .pipe(
        tap(_ => console.log(`User login, should get back User`)),
        catchError(response => this.handleError(response, response.error['error']))
      ).subscribe((response: HttpResponse<any>) => {
        console.log(response);
        console.log(response.headers.get('Authorization'));
        this.auth.sendToken(response.headers.get('Authorization'));
        if (response.body) {
          console.log(response.body);
          this.user.next(response['data']);
        }
    });
  }

  getUser(userId: number | null): Observable<User> {
    return this.http.get<any>(`${this.baseUrl}/user?userId=${userId}`)
      .pipe(
        tap(_ => console.log(`Getting User`)),
        catchError(response => this.handleError(response, response.error['error']))
      );
  }

  private handleError<T> (error: HttpErrorResponse, errorMessages: HttpErrorResponse, result?: T) {
    console.error(error);
    console.error(errorMessages['message']);
    console.error(errorMessages['cause']);
    return of(result as T);
  }

  logoutUser() {
    this.auth.logout();
  }

}
