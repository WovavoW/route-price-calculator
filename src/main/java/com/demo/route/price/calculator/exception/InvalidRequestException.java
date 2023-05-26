package com.demo.route.price.calculator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidRequestException extends BaseBusinessException {
    public InvalidRequestException(String message) {
        super(message);
    }
}
