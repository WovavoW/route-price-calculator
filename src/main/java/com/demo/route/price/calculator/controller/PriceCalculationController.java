package com.demo.route.price.calculator.controller;


import com.demo.route.price.calculator.api.PriceCalculationApi;
import com.demo.route.price.calculator.api.PriceCalculationFlowService;
import com.demo.route.price.calculator.api.PriceCalculationRequestValidationService;
import com.demo.route.price.calculator.model.request.PriceCalculationRequest;
import com.demo.route.price.calculator.model.response.TicketBundleResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PriceCalculationController implements PriceCalculationApi {

    private final PriceCalculationFlowService priceCalculationFlowService;

    private final PriceCalculationRequestValidationService requestValidationService;

    public PriceCalculationController(
            PriceCalculationFlowService priceCalculationFlowService,
            PriceCalculationRequestValidationService requestValidationService
    ) {
        this.requestValidationService = requestValidationService;
        this.priceCalculationFlowService = priceCalculationFlowService;
    }

    @Override
    public TicketBundleResponse calculatePrices(@Valid PriceCalculationRequest request) {
        requestValidationService.validate(request);
        return priceCalculationFlowService.calculatePrices(request);
    }
}