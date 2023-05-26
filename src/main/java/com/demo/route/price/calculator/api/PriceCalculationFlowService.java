package com.demo.route.price.calculator.api;

import com.demo.route.price.calculator.model.request.PriceCalculationRequest;
import com.demo.route.price.calculator.model.response.TicketBundleResponse;


public interface PriceCalculationFlowService {

    TicketBundleResponse calculatePrices(PriceCalculationRequest request);
}
