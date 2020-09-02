package ru.blogic.enums;

import org.springframework.security.core.GrantedAuthority;
import ru.blogic.interfaces.RoleName;

/**
 * Роли
 *
 * @author DSalikhov
 */
public enum RoleNameImpl implements RoleName {

    ROLE_USER("ROLE.USER", "Предоставляет базовые права доступа"), ROLE_ADMIN("ROLE.ADMIN", "Предоставляет расширенные права доступа");

    private final String code;

    private final String description;

    RoleNameImpl(String code, String desc) {
        this.code = code;
        this.description = desc;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
