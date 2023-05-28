package com.demo.route.price.calculator.client.service;

import com.demo.route.price.calculator.client.VatRateClient;
import com.demo.route.price.calculator.exception.IntegrationFailureException;
import com.demo.route.price.calculator.model.response.VatRateServiceWrappedResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
public class VatRateClientWrapperService {

    private final Logger logger = LoggerFactory.getLogger(VatRateClientWrapperService.class);

    private final VatRateClient vatRateClient;

    public VatRateClientWrapperService(VatRateClient vatRateClient) {
        this.vatRateClient = vatRateClient;
    }

    @Async
    public CompletableFuture<VatRateServiceWrappedResponse> getVatRate(LocalDate date) {
        try {
            logCall(date);
            final var response = vatRateClient.getVatRate(date);
            Objects.requireNonNull(response);
            return CompletableFuture.completedFuture(response);
        } catch (Exception e) {
            logger.error(String.format("Failed to call vat-rate service for parameters: date [%s]", date));
            throw new IntegrationFailureException("Failed to call vat-rate service");
        }
    }

    private void logCall(LocalDate date) {
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("calling - vat rate client for: date [%s]", date));
        }
    }
}
