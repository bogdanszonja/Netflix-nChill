import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable, of, Subject } from 'rxjs';
import { tap, catchError, map } from 'rxjs/operators';

import { Series } from '../../models/Series';

@Injectable({
  providedIn: 'root'
})
export class SeriesService {

  private baseUrl = 'http://localhost:8080';
  searchResult = new Subject<Series[]>();

  constructor(private http: HttpClient) { }

  getAllSeries(): Observable<Series[]> {
    return this.http.get<Series[]>(`${this.baseUrl}/series`).pipe(
      tap(_ => console.log(`Series found!`)),
      map(response => {
        return response['data'];
      }),
      catchError(this.handleError<Series[]>())
    );
  }

  getSingleSeries(id: number): Observable<Series> {
    return this.http.get<Series>(`${this.baseUrl}/series?id=${id}`).pipe(
      tap(_ => console.log(`Series id=${id} found!`)),
      map(response => {
        return response['data'];
      }),
      catchError(this.handleError<Series>())
      );
  }

  searchSeries(searchTerm: string): Observable<Series[]> {
    if (!searchTerm.trim()) { return of([]); }

    this.http.get<Series[]>(`${this.baseUrl}/search?searchTerm=${searchTerm}`)
      .pipe(
        tap(_ => console.log(`More series found!`)),
        map(response => {
          return response['data'];
        }),
        catchError(this.handleError<Series[]>())
      ).subscribe(series => {
          this.searchResult.next(series);
          console.log(this.searchResult);
      });
  }

  private handleError<T> (result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    };
  }

}
