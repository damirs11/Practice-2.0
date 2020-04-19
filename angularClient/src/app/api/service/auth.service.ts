import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of, BehaviorSubject } from 'rxjs';
import { catchError, tap, map } from 'rxjs/operators';

const apiUrl = 'api/auth/';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  isLoggedIn = false;
  redirectUrl: string;

  constructor(private http: HttpClient) {
  }

  login(data: any): Observable<any> {
    return this.http.post<any>(apiUrl + 'login', data).pipe(
      tap((_) => (this.isLoggedIn = true)),
      catchError(this.handleError('login', []))
    );
  }

  logout(): Observable<any> {
    return this.http.get<any>(apiUrl + 'sighout').pipe(
      tap((_) => (this.isLoggedIn = false)),
      catchError(this.handleError('logout', []))
    );
  }

  register(data: any): Observable<any> {
    return this.http.post<any>(apiUrl + 'register', data).pipe(
      tap((_) => this.log('login')),
      catchError(this.handleError('login', []))
    );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      this.login(`${operation} failed: ${error.message}`);

      return of(result as T);
    };
  }

  private log(message: string) {
    console.log(message);
  }
}
