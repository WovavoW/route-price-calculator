package com.demo.route.price.calculator.service.integration;

import com.demo.route.price.calculator.client.service.BasePriceClientWrapperService;
import com.demo.route.price.calculator.client.service.VatRateClientWrapperService;
import com.demo.route.price.calculator.exception.IntegrationFailureException;
import com.demo.route.price.calculator.model.request.PriceCalculationRequest;
import com.demo.route.price.calculator.service.data.ExternalData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletionException;

@Service
public class IntegrationServiceImpl implements IntegrationService {

    private final Logger logger = LoggerFactory.getLogger(IntegrationServiceImpl.class);

    private final BasePriceClientWrapperService basePriceService;

    private final VatRateClientWrapperService vatRateService;


    public IntegrationServiceImpl(BasePriceClientWrapperService basePriceService, VatRateClientWrapperService vatRateService) {
        this.basePriceService = basePriceService;
        this.vatRateService = vatRateService;
    }

    public ExternalData asyncObtainPriceCalculationDataFromExternalClients(PriceCalculationRequest request) {
        final var futureVatRateResponse = vatRateService.getVatRate(request.date());
        final var futureBaseRateResponse = basePriceService.getBaseRoutePrice(
                request.routeName(),
                request.date()
        );
        try {
            final var vatRate = futureVatRateResponse.join().vatRate();
            final var basePrice = futureBaseRateResponse.join().basePrice();

            return new ExternalData(vatRate, basePrice);
        } catch (CompletionException e) {
            logger.error("Calls to remote clients failed", e);
            throw new IntegrationFailureException("Failed to call remote services");
        }
    }
}
