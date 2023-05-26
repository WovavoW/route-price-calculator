package com.demo.route.price.calculator.service.validation;

import com.demo.route.price.calculator.api.PriceCalculationRequestValidationService;
import com.demo.route.price.calculator.exception.InvalidRequestException;
import com.demo.route.price.calculator.model.request.PriceCalculationRequest;
import com.demo.route.price.calculator.service.validation.validator.RequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceCalculationRequestValidationServiceImpl implements PriceCalculationRequestValidationService {

    private final Logger logger = LoggerFactory.getLogger(PriceCalculationRequestValidationServiceImpl.class);

    private final List<RequestValidator<PriceCalculationRequest>> validators;

    public PriceCalculationRequestValidationServiceImpl(List<RequestValidator<PriceCalculationRequest>> validators) {
        this.validators = validators;
    }

    private static String formatLogMessage(PriceCalculationRequest request, StringBuilder stringBuilder) {
        return String.format("Request [%s] validation failed for with errors: %s", request, stringBuilder);
    }

    @Override
    public void validate(PriceCalculationRequest request) {
        var failedResults = validators.stream()
                .map(v -> v.validate(request))
                .filter(result -> !result.result())
                .toList();

        if (!failedResults.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            failedResults.forEach(result -> stringBuilder.append("\n").append(result.errorMessage()));
            if (logger.isErrorEnabled()) {
                logger.error(formatLogMessage(request, stringBuilder));
            }
            throw new InvalidRequestException(String.format("Request is invalid due to following errors: %s", stringBuilder));
        }
    }
}
