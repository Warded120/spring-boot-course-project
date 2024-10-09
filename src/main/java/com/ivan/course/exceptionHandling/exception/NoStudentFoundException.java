package com.ivan.course.exceptionHandling.exception;

import com.ivan.course.exceptionHandling.IWhatMessage;

public class NoStudentFoundException extends RuntimeException implements IWhatMessage {
    private final String message;

    public NoStudentFoundException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String what() {
        return message;
    }
}
