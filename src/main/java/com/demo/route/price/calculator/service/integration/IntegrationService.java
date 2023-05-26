package com.demo.route.price.calculator.service.integration;

import com.demo.route.price.calculator.model.response.BasePriceServiceWrappedResponse;
import com.demo.route.price.calculator.model.response.VatRateServiceWrappedResponse;

import java.time.LocalDate;

public interface IntegrationService {

    VatRateServiceWrappedResponse getVatRate(LocalDate date);

    BasePriceServiceWrappedResponse getBaseRoutePrice(String routeName, LocalDate date);
}
