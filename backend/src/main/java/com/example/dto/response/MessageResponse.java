package com.example.dto.response;

/**
 * DTO для отравки ответа
 *
 * @author DSalikhov
 */
public class MessageResponse {
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
