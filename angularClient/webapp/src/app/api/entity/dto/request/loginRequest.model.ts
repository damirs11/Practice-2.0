/**
 * DTO для отправки запроса на логин
 *
 * @author DSalikhov
 * @export
 */
export class LoginRequest {
    constructor(username: string, password: string) {
        this.username = username;
        this.password = password;
    }

    /**
     *  Логин пользователя
     */
    username: string;

    /**
     * Пароль
     */
    password: string;
}
