import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {flatMap, map, tap} from 'rxjs/operators';
import {User} from '../../entity/user';
import {LoggerService} from '../../../shared/logger/logger.service';
import {RegisterRequest} from "../../entity/dto/request/registerRequest.model";
import {MessageResponse} from "../../entity/dto/response/messageResponse.model";
import {LoginRequest} from "../../entity/dto/request/loginRequest.model";

const apiUrl = 'api/auth';

@Injectable({
    providedIn: 'root',

})
export class AuthService {
    private currentUserSubject: BehaviorSubject<User>;
    public currentUser: Observable<User>;

    constructor(private http: HttpClient, private logger: LoggerService) {
    }

    get currentUserValue(): User {
        return this.currentUserSubject.value;
    }

    login(data: LoginRequest): Observable<User> {
        return this.http.post<MessageResponse>(`${apiUrl}/login`, data).pipe(
            tap((message) => this.logger.log(`AuthService.login: ${message.message}`)),
            flatMap((_) => this.updateUserStatus()),
        )
    }

    registration(data: RegisterRequest): Observable<MessageResponse> {
        return this.http.post<MessageResponse>(`${apiUrl}/registration`, data).pipe(
            tap((message) => this.logger.log(`AuthService.registration: ${message.message}`)),
        );
    }

    logout(): Observable<User> {
        return this.http.get(`${apiUrl}/logout`).pipe(
            tap(() => this.logger.log('AuthService.registration: Выход прошел успешно')),
            flatMap(() => this.updateUserStatus()),
        );
    }

    updateUserStatus(): Observable<User> {
        return this.http.get<User>(`${apiUrl}/currentUser`).pipe(
            tap((user) => this.logger.log(`AuthService.userStatus:`, user)),
            tap((user) => this.currentUserSubject.next(user)),
        );
    }

    initService(): Promise<User> {
        this.logger.log("Создаем сервис");

        this.currentUserSubject = new BehaviorSubject<User>(null);
        this.currentUser = this.currentUserSubject.asObservable();

        return this.updateUserStatus().pipe(
            tap((user) => {
                this.currentUserSubject.next(user);

                this.logger.log("Сервис создан");
            })
        ).toPromise();
    }
}
