package com.ivan.course.exceptionHandling.exception;

import com.ivan.course.exceptionHandling.IWhatMessage;

public class NoUserFoundException extends RuntimeException implements IWhatMessage {

    private final String message;
    private final String username;

    public NoUserFoundException(String message, String username) {
        super(message);
        this.message = message;
        this.username = username;
    }

    public String what() {
        return message + ": " + username;
    }
}
