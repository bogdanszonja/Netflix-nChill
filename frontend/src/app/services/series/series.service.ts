import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';

import { Observable, of, Subject } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';

import { Series } from '../../models/Series';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': sessionStorage.getItem('token')
  })
};

@Injectable({
  providedIn: 'root'
})
export class SeriesService {

  private baseUrl = 'http://localhost:8080';
  searchResult = new Subject<Series[]>();

  constructor(private http: HttpClient) { }

  getAllSeries(): Observable<Series[]> {
    if ('azta') { return of([]); }

    this.http.get<any>(`${this.baseUrl}/series`, httpOptions)
      .pipe(
      tap(_ => console.log(`Series found!`)),
      catchError(response => this.handleError(response, response.error['error']))
    ).subscribe(response => {
      if (response) {
        this.searchResult.next(response['data']);
        console.log(this.searchResult);
      }
    });
  }

  getSingleSeries(id: number): Observable<Series> {
    return this.http.get<any>(`${this.baseUrl}/series?id=${id}`).pipe(
      tap(_ => console.log(`Series id=${id} found!`)),
      catchError(response => this.handleError(response, response.error['error']))
      );
  }

  searchSeries(searchTerm: string): Observable<Series[]> {
    if (!searchTerm.trim()) { return of([]); }

    this.http.get<Series[]>(`${this.baseUrl}/series`,
      { headers: {'Authorization': sessionStorage.getItem('token')} })
      .pipe(
        tap(_ => console.log(`More series found!`)),
        catchError(response => this.handleError(response, response.error['error']))
      ).subscribe(response => {
        console.log(response);
        if (response) {
          this.searchResult.next(response['data']);
          console.log(this.searchResult);
        }
      });
  }

  private handleError<T> (error: HttpErrorResponse, errorMessages: HttpErrorResponse, result?: T) {
    console.error(error);
    console.error(errorMessages['message']);
    console.error(errorMessages['cause']);
    return of(result as T);
  }

}
