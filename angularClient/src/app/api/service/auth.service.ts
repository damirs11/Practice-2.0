import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of, BehaviorSubject } from 'rxjs';
import { catchError, tap, map } from 'rxjs/operators';
import { AuthUser } from '../entity/authUser';

const apiUrl = 'http://localhost:8080/api/auth/';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private currentUserSubject: BehaviorSubject<AuthUser>;
  public currentUser: Observable<AuthUser>;

  isLoggedIn = false;
  redirectUrl: string;

  constructor(private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<AuthUser>(
      JSON.parse(localStorage.getItem('currentUser'))
    );
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): AuthUser {
    return this.currentUserSubject.value;
  }

  login(data: any) {
    return this.http.post<any>(apiUrl + 'login', data).pipe(
      map((user) => {
        localStorage.setItem('currentUser', JSON.stringify(user));
        this.currentUserSubject.next(user);
        return user;
      }),
      catchError(this.handleError('login', []))
    );
  }

  register(data: any) {
    return this.http.post<any>(apiUrl + 'register', data).pipe(
      tap((_) => this.log('register')),
      catchError(this.handleError('register', []))
    );
  }

  logout() {
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
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
