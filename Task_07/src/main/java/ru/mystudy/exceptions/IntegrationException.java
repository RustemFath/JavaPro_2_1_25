package ru.mystudy.exceptions;

import lombok.Getter;

public class IntegrationException extends RuntimeException {

    @Getter
    private final String statusCode;

    public IntegrationException(String message, String statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
