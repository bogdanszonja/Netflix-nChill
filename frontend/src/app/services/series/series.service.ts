import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import {Observable, of, Subject} from 'rxjs';
import { tap, catchError } from 'rxjs/operators';

import { Series } from '../../models/Series';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class SeriesService {

  private seriesUrl = 'http://localhost:8080/series';
  private searchUrl = 'http://localhost:8080/search';
  searchResult = new Subject<Series[]>();

  constructor(private http: HttpClient) { }

  getAllSeries(): Observable<Series[]> {
    return this.http.get<Series[]>(this.seriesUrl).pipe(
      tap(_ => console.log(`Series found!`)),
      catchError(this.handleError<Series[]>())
    );
  }

  getSingleSeries(id: number): Observable<Series> {
    return this.http.get<Series>(`${this.seriesUrl}?id=${id}`).pipe(
      tap(_ => console.log(`Series id=${id} found!`)),
      catchError(this.handleError<Series>())
      );
  }

  searchSeries(searchTerm: string): Observable<Series[]> {
    if (!searchTerm.trim()) { return of([]); }

    this.http.post<Series[]>(`${this.searchUrl}?searchTerm=${searchTerm}`, {'searchTerm': searchTerm}, httpOptions)
      .pipe(
        tap(_ => console.log(`More series found!`)),
        catchError(this.handleError<Series[]>())
      ).subscribe(series => {
          this.searchResult.next(series);
          console.log(this.searchResult);
      });
  }

  // sendSeries(series: Series[]) {
  //   console.log(series);
  //   this.searchResult.next(this.series) = series;
  // }

  private handleError<T> (result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    };
  }

}
