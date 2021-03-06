import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

import { Observable, of, Subject } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';

import { Series } from '../../models/Series';

@Injectable({
  providedIn: 'root'
})
export class SeriesService {

  private baseUrl = 'http://localhost:8080';
  searchResult = new Subject<Series[]>();

  constructor(private http: HttpClient) { }

  getAllSeries(): Observable<Series[]> {
    if ('azta') { return of([]); }

    this.http.get<any>(`${this.baseUrl}/series`)
      .pipe(
      tap(_ => console.log(`Series found!`)),
      catchError(response => this.handleError(response))
    ).subscribe(response => {
      if (response) {
        this.searchResult.next(response['data']);
        console.log(this.searchResult);
      }
    });
  }

  getSingleSeries(id: number): Observable<Series> {
    return this.http.get<any>(`${this.baseUrl}/series/${id}`).pipe(
      tap(_ => console.log(`Series id=${id} found!`)),
      catchError(response => this.handleError(response))
      );
  }

  searchSeries(searchTerm: string): Observable<Series[]> {
    if (!searchTerm.trim()) { return of([]); }

    this.http.get<Series[]>(`${this.baseUrl}/series/search?searchTerm=${searchTerm}`)
      .pipe(
        tap(_ => console.log(`More series found!`)),
        catchError(response => this.handleError(response))
      ).subscribe((response: Series[]) => {
        console.log(response);
        if (response) {
          this.searchResult.next(response);
          console.log(this.searchResult);
        }
      });
  }

  private handleError<T> (error: HttpErrorResponse, result?: T) {
    console.error(error);
    console.error(error.error['error']);
    console.error(error.error['message']);
    return of(result as T);
  }

}
