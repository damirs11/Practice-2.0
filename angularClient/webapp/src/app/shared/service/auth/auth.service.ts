import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {tap} from 'rxjs/operators';
import {User} from '../../../api/entity/auth/user';
import {LoggerService} from '../logger/logger.service';
import {Register} from '../../../api/entity/auth/register';
import {MessageResponse} from '../../../api/entity/response/messageResponse';
import {Login} from '../../../api/entity/auth/login';

const apiUrl = 'api/auth';

/**
 * Сервис для работы с безопасностью
 *
 * @author DSalikhov
 * @export
 */
@Injectable({
    providedIn: 'root',

})
export class AuthService {
    constructor(private http: HttpClient, private logger: LoggerService) {
    }

    /**
     * Логин с обновление статуса
     *
     * @param data LoginRequest - сущность с данными для логина
     */
    login(data: Login) {
        return this.http.post<MessageResponse>(`${apiUrl}/login`, data).pipe(
            tap((message) => this.logger.log(`AuthService.login: ${message.message}`)),
        );
    }

    /**
     * Регистрация
     *
     * @param data Register - сущность с данными для регистрации
     */
    registration(data: Register): Observable<MessageResponse> {
        return this.http.post<MessageResponse>(`${apiUrl}/registration`, data).pipe(
            tap((message) => this.logger.log(`AuthService.registration: ${message.message}`)),
        );
    }

    /**
     * Выход с обновление статуса
     *
     * @author DSalikhov
     */
    logout(): Observable<void> {
        return this.http.get<void>(`${apiUrl}/logout`).pipe(
            tap(() => this.logger.log('AuthService.registration: Выход прошел успешно')),
        );
    }

    /**
     * Запрашивает текущий статус пользователя
     */
    updateUserStatus(): Observable<User> {
        return this.http.get<User>(`${apiUrl}/currentUser`).pipe(
            tap((user) => this.logger.log(`AuthService.userStatus:`, user)),
        );
    }
}
