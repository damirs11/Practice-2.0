package com.example.dto.request;

import javax.validation.constraints.NotBlank;

/**
 * DTO для отправки запроса на логин
 *
 * @author DSalikhov
 */
public class LoginRequest {

    /**
     *  Логин пользователя
     */
    @NotBlank
    private String username;

    /**
     * Пароль
     */
    @NotBlank
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
