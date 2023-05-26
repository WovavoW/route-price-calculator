package com.demo.route.price.calculator.service.validation.validator;

import org.springframework.lang.NonNull;

public record ValidationResult(
        @NonNull Boolean result,
        String errorMessage
) {
}
