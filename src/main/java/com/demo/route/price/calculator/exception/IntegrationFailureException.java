package com.demo.route.price.calculator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class IntegrationFailureException extends BaseBusinessException {
    public IntegrationFailureException(String message) {
        super(message);
    }
}
