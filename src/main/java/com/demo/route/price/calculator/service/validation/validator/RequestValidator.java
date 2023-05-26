package com.demo.route.price.calculator.service.validation.validator;

public abstract class RequestValidator<T> {

    public final ValidationResult validate(T request) {
        boolean validationResult = innerValidation(request);
        return new ValidationResult(
                validationResult,
                validationResult ? null : getErrorMessage(request)
        );
    }

    protected abstract boolean innerValidation(T request);

    protected abstract String getErrorMessage(T request);
}
