package com.demo.route.price.calculator.service.integration;

import com.demo.route.price.calculator.client.BasePriceClient;
import com.demo.route.price.calculator.client.VatRateClient;
import com.demo.route.price.calculator.exception.IntegrationFailureException;
import com.demo.route.price.calculator.model.response.BasePriceServiceWrappedResponse;
import com.demo.route.price.calculator.model.response.VatRateServiceWrappedResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

@Service
public class IntegrationServiceImpl implements IntegrationService {

    private final Logger logger = LoggerFactory.getLogger(IntegrationServiceImpl.class);

    private final BasePriceClient basePriceClient;

    private final VatRateClient vatRateClient;


    public IntegrationServiceImpl(BasePriceClient basePriceClient, VatRateClient vatRateClient) {
        this.basePriceClient = basePriceClient;
        this.vatRateClient = vatRateClient;
    }

    public VatRateServiceWrappedResponse getVatRate(LocalDate date) {
        try {
            final var response = vatRateClient.getVatRate(date);
            Objects.requireNonNull(response);

            return response;
        } catch (Exception e) {
            logger.error(String.format("Failed to call vat-rate service for parameters: date [%s]", date));
            throw new IntegrationFailureException("Failed to call vat-rate service");
        }
    }

    public BasePriceServiceWrappedResponse getBaseRoutePrice(String routeName, LocalDate date) {
        try {
            final var response = basePriceClient.getBaseRoutePrice(routeName, date);
            Objects.requireNonNull(response);

            return response;
        } catch (Exception e) {
            logger.error(String.format("Failed to call base-price service for parameters: routeName [%s] date [%s]", routeName, date));
            throw new IntegrationFailureException("Failed to call base-price service");
        }
    }
}
