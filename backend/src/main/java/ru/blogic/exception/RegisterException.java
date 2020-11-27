package ru.blogic.exception;

import java.security.GeneralSecurityException;

public class RegisterException extends GeneralSecurityException {
    public RegisterException() {
        super();
    }

    public RegisterException(String msg) {
        super(msg);
    }

    public RegisterException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegisterException(Throwable cause) {
        super(cause);
    }
}
