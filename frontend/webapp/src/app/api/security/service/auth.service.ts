import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of, BehaviorSubject } from 'rxjs';
import { catchError, tap, map } from 'rxjs/operators';
import * as jwt_decode from 'jwt-decode';
import { JwtResponse } from '../payload/response/jwtResponse.model';
import { LoginRequest } from '../payload/request/loginRequest.model';
import { RegisterRequest } from '../payload/request/registerRequest.model';
import { MessageResponse } from '../payload/response/messageResponse.model';

const apiUrl = 'http://localhost:8080/api/auth/';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private currentUserSubject: BehaviorSubject<JwtResponse>;
  public currentUser: Observable<JwtResponse>;

  isLoggedIn = false;
  redirectUrl: string;

  constructor(private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<JwtResponse>(
      JSON.parse(localStorage.getItem('currentUser'))
    );
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): JwtResponse {
    return this.currentUserSubject.value;
  }

  login(data: LoginRequest | any) {
    return this.http.post<JwtResponse>(apiUrl + 'signin', data).pipe(
      map((user) => {
        localStorage.setItem('currentUser', JSON.stringify(user));

        this.currentUserSubject.next(user);

        return user;
      }),
      catchError(this.handleError('login', []))
    );
  }

  register(data: RegisterRequest | any) {
    return this.http.post<MessageResponse>(apiUrl + 'signup', data).pipe(
      tap((message) => this.log(`AuthService.register: ${message.message}`)),
      catchError(this.handleError('AuthService.register', []))
    );
  }

  logout() {
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
  }

  getTokenExpirationDate(token: string): Date {
    const decoded = jwt_decode(token);
    console.log(decoded);

    if (decoded.exp === undefined) {
      return null;
    }

    const date = new Date(0);
    date.setUTCSeconds(decoded.exp);
    return date;
  }

  isTokenExpired(token?: string): boolean {
    if (!token) {
      token = this.currentUserValue.token;
    }
    if (!token) {
      return true;
    }
    const date = this.getTokenExpirationDate(token);
    if (date === undefined) {
      return false;
    }
    return !(date.valueOf() > new Date().valueOf());
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      this.log(`${operation} failed: ${error.message}`);

      return of(result as T);
    };
  }

  private log(message: string) {
    console.log(message);
  }
}
