package com.demo.route.price.calculator.api;

import com.demo.route.price.calculator.controller.annotation.ValidateApiVersion;
import com.demo.route.price.calculator.model.request.PriceCalculationRequest;
import com.demo.route.price.calculator.model.response.TicketBundleResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/price")
public interface PriceCalculationApi {

    @PostMapping
    @ValidateApiVersion
    TicketBundleResponse calculatePrices(@RequestBody PriceCalculationRequest request);
}
