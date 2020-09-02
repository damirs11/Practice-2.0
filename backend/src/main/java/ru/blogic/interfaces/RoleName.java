package ru.blogic.interfaces;

import org.springframework.security.core.GrantedAuthority;

/**
 * Интерфейс для множества ролей
 *
 * @author DSalikhov
 */
public interface RoleName extends GrantedAuthority {

    String getCode();

    String getDescription();

    @Override
    String getAuthority();
}
