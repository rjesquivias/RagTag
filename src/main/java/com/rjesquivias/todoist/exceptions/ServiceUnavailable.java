package com.rjesquivias.todoist.exceptions;

public final class ServiceUnavailable extends RuntimeException {

    public ServiceUnavailable(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

    public ServiceUnavailable(String errorMessage) {
        super(errorMessage);
    }
}
