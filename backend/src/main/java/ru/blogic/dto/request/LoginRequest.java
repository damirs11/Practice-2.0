package ru.blogic.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * DTO для отправки запроса на логин
 *
 * @author DSalikhov
 */
public class LoginRequest {

    /**
     *  Логин пользователя
     */
    @NotEmpty
    private String username;

    /**
     * Пароль
     */
    @NotEmpty
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}