import {Authority} from './authority';

export class User {
    id: number;
    username: string;
    password: string;
    authorities: Array<Authority>;
    enabled: boolean;
    accountNonLocked: boolean;
    credentialsNonExpired: boolean;
    accountNonExpired: boolean;
}
