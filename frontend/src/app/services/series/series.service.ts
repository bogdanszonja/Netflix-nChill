import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';

import { Series } from '../../models/Series';

@Injectable({
  providedIn: 'root'
})
export class SeriesService {

  private seriesUrl = 'http://localhost:8080/series';

  constructor(private http: HttpClient) { }

  getAllSeries(): Observable<Series[]> {
    return this.http.get<Series[]>(this.seriesUrl).pipe(
      tap(_ => console.log(`Titles found!`)),
      catchError(this.handleError<Series[]>())
    );
  }

  getSingleSeries(id: number): Observable<Series> {
    return this.http.get<Series>(`${this.seriesUrl}/?id=${id}`).pipe(
      tap(_ => console.log(`Series found!`)),
      catchError(this.handleError<Series>())
      );
  }

  searchSeries(searchTerm: string): Observable<Series[]> {
    if (!searchTerm.trim()) { return of([]); }

    return this.http.get<Series[]>(`${this.seriesUrl}/?searchTerm=${searchTerm}`).pipe(
      tap(_ => console.log(`More series found!`)),
      catchError(this.handleError<Series[]>())
      );
  }

  private handleError<T> (result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    };
  }

}
