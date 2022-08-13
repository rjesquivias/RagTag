package com.rjesquivias.todoist;

final class ServiceUnavailableException extends RuntimeException {

    public ServiceUnavailableException(String errorMessage) {
        super(errorMessage);
    }
}
