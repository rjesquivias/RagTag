package com.rjesquivias.todoist.exceptions;

public class ServiceUnavailableException extends RuntimeException {

  public ServiceUnavailableException(String errorMessage, Throwable err) {
    super(errorMessage, err);
  }

  public ServiceUnavailableException(String errorMessage) {
    super(errorMessage);
  }
}
