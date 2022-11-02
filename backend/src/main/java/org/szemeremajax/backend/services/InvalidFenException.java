package org.szemeremajax.backend.services;

public class InvalidFenException extends RuntimeException {
    public InvalidFenException(String message) {
        super(message);
    }

    public InvalidFenException(String message, Throwable cause) {
        super(message, cause);
    }
}
