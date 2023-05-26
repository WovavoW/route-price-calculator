package com.demo.route.price.calculator.service.validation.validator;

import com.demo.route.price.calculator.model.request.PriceCalculationRequest;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class PassengerNumberValidator extends RequestValidator<PriceCalculationRequest> {

    @Override
    protected boolean innerValidation(PriceCalculationRequest request) {
        final Set<Integer> numbers = new HashSet<>();
        return request.passengers().stream().allMatch(passenger -> {
            boolean unique = !numbers.contains(passenger.number());
            numbers.add(passenger.number());
            return unique;
        });
    }

    @Override
    protected String getErrorMessage(PriceCalculationRequest request) {
        return "Each passenger in request has to have distinct number";
    }
}
