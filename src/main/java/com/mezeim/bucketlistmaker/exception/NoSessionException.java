package com.mezeim.bucketlistmaker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class NoSessionException extends RuntimeException {
    public NoSessionException() {
        super();
    }

    public NoSessionException(String message) {
        super(message);
    }

    public NoSessionException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSessionException(Throwable cause) {
        super(cause);
    }

}
