package com.mezeim.bucketlistmaker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NotValidException extends RuntimeException{

    public NotValidException() {
        super();
    }

    public NotValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotValidException(String message) {
        super(message);
    }

    public NotValidException(Throwable cause) {
        super(cause);
    }
}
