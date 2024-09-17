package com.ivan.course.exceptionHandling;

public class UserNotFoundException extends Exception implements IWhatMessage {

    private String message;
    private String username;

    public UserNotFoundException(String message, String username) {
        super(message);
    }

    public String what() {
        return message + ": " + username;
    }
}
