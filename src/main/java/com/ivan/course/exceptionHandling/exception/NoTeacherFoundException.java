package com.ivan.course.exceptionHandling.exception;

import com.ivan.course.exceptionHandling.IWhatMessage;

public class NoTeacherFoundException extends RuntimeException implements IWhatMessage {
  private final String message;

  public NoTeacherFoundException(String message) {
    super(message);
    this.message = message;
  }

  @Override
  public String what() {
    return message;
  }
}
