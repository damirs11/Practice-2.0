package ru.blogic.dto.response;

/**
 * DTO для отправки ответа
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
