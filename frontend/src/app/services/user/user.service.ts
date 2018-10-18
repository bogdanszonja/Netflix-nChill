import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Subject } from 'rxjs/internal/Subject';
import { catchError, tap } from 'rxjs/operators';
import { of } from 'rxjs';

import { Series } from '../../models/Series';
import { Observable } from 'rxjs/internal/Observable';
import { Season } from '../../models/Season';
import { Episode } from '../../models/Episode';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private url = 'http://localhost:8080/user';

  loginStatus = new Subject<string>();

  constructor(private http: HttpClient) { }

  handleLogin(type: string) {
    console.log(this.loginStatus);
    this.loginStatus.next(type);
  }

  addWholeSeries(series: Series): Observable<Series> {
    return this.http.post<Series>(this.url, {'type': 'series', 'series': series}, httpOptions)
      .pipe(
        tap(_ => console.log(`Series added`)),
        catchError(this.handleError<Series>())
      );
  }

  addSingleSeason(season: Season): Observable<Season> {
    return this.http.post<Season>(this.url, {'type': 'season', 'season': season}, httpOptions)
      .pipe(
        tap(_ => console.log(`Season added`)),
        catchError(this.handleError<Season>())
      );
  }

  addSingleEpisode(episode: Episode): Observable<Episode> {
    return this.http.post<Episode>(this.url, {'type': 'episode', 'episode': episode}, httpOptions)
      .pipe(
        tap(_ => console.log(`Episode added`)),
        catchError(this.handleError<Episode>())
      );
  }

  private handleError<T> (result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    };
  }

}
