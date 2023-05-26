package com.demo.route.price.calculator.api;

import com.demo.route.price.calculator.model.request.PriceCalculationRequest;


public interface PriceCalculationRequestValidationService {

    void validate(PriceCalculationRequest request);
}
