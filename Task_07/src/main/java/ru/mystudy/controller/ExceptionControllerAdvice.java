package ru.mystudy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.mystudy.dto.ErrorResponse;
import ru.mystudy.dto.IntegrationErrorResponse;
import ru.mystudy.exceptions.IntegrationException;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse illegalArgumentException(IllegalArgumentException exception) {
        return new ErrorResponse( "Ошибка параметров запроса", exception.getMessage());
    }

    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ExceptionHandler(IntegrationException.class)
    public IntegrationErrorResponse integrationException(IntegrationException exception) {
        return new IntegrationErrorResponse(
                "Ошибка взаимодействия с внешней интеграцией", exception.getStatusCode(), exception.getMessage());
    }

}
