package com.demo.route.price.calculator.client.service;

import com.demo.route.price.calculator.client.BasePriceClient;
import com.demo.route.price.calculator.exception.IntegrationFailureException;
import com.demo.route.price.calculator.model.response.BasePriceServiceWrappedResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
public class BasePriceClientWrapperService {

    private final Logger logger = LoggerFactory.getLogger(BasePriceClientWrapperService.class);

    private final BasePriceClient basePriceClient;

    public BasePriceClientWrapperService(BasePriceClient basePriceClient) {
        this.basePriceClient = basePriceClient;
    }

    @Async
    public CompletableFuture<BasePriceServiceWrappedResponse> getBaseRoutePrice(String routeName, LocalDate date) {
        try {
            logCall(routeName, date);
            final var response = basePriceClient.getBaseRoutePrice(routeName, date);
            Objects.requireNonNull(response);
            return CompletableFuture.completedFuture(response);
        } catch (Exception e) {
            logger.error(String.format("Failed to call base-price service for parameters: routeName [%s] date [%s]", routeName, date));
            throw new IntegrationFailureException("Failed to call base-price service");
        }
    }

    private void logCall(String routeName, LocalDate date) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("calling - base route price client for: routeName [%s], date [%s]", routeName, date));
        }
    }
}
