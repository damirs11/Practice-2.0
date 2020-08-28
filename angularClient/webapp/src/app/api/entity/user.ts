import {Authority} from './authority';

/**
 * Пользователь
 *
 * @author DSalikhov
 * @export
 */
export class User {

    /**
     * ID
     */
    id: number;

    /**
     * Имя
     */
    username: string;

    /**
     * Пароль
     */
    password: string;

    /**
     * Массив доступных ролей
     */
    authorities: Array<Authority>;

    enabled: boolean;
    accountNonLocked: boolean;
    credentialsNonExpired: boolean;
    accountNonExpired: boolean;
}
