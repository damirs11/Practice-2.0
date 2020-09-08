/**
 * DTO для отправки запроса на регистрацию
 *
 * @author DSalikhov
 * @export
 */
export class Register {
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
