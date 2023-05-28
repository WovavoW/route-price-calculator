package com.demo.route.price.calculator.service.integration;

import com.demo.route.price.calculator.client.service.BasePriceClientWrapperService;
import com.demo.route.price.calculator.client.service.VatRateClientWrapperService;
import com.demo.route.price.calculator.exception.IntegrationFailureException;
import com.demo.route.price.calculator.model.request.PriceCalculationRequest;
import com.demo.route.price.calculator.model.response.BasePriceServiceWrappedResponse;
import com.demo.route.price.calculator.model.response.VatRateServiceWrappedResponse;
import com.demo.route.price.calculator.util.AbstractTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

class IntegrationServiceImplTest extends AbstractTest {

    private final IntegrationService integrationService;
    PriceCalculationRequest request = getPriceCalculationRequest();
    BasePriceServiceWrappedResponse basePriceResponse = new BasePriceServiceWrappedResponse(BASE_PRICE);
    VatRateServiceWrappedResponse vatRateResponse = new VatRateServiceWrappedResponse(TAX_RATE);

    @Mock
    private BasePriceClientWrapperService basePriceClient;
    @Mock
    private VatRateClientWrapperService vatRateClient;

    public IntegrationServiceImplTest() {
        MockitoAnnotations.openMocks(this);
        integrationService = new IntegrationServiceImpl(basePriceClient, vatRateClient);
    }

    @Test
    void getExternalDataSuccess() {
        // set-up
        Mockito.when(vatRateClient.getVatRate(request.date()))
                .thenReturn(CompletableFuture.completedFuture(vatRateResponse));
        Mockito.when(basePriceClient.getBaseRoutePrice(request.routeName(), request.date()))
                .thenReturn(CompletableFuture.completedFuture(basePriceResponse));

        //execute and verify
        assertDoesNotThrow(() -> {
            var result = integrationService.asyncObtainPriceCalculationDataFromExternalClients(request);
            assertEquals(BASE_PRICE, result.basePrice());
            assertEquals(TAX_RATE, result.vatRate());
        });

        Mockito.verify(vatRateClient).getVatRate(request.date());
        Mockito.verify(basePriceClient).getBaseRoutePrice(request.routeName(), request.date());
    }

    @Test
    void getExternalDataFailureVatRateClientCallException() {
        // set-up
        Mockito.when(vatRateClient.getVatRate(request.date()))
                .thenReturn(CompletableFuture.failedFuture(new IntegrationFailureException("message")));
        Mockito.when(basePriceClient.getBaseRoutePrice(request.routeName(), request.date()))
                .thenReturn(CompletableFuture.completedFuture(basePriceResponse));

        //execute and verify
        assertThrows(IntegrationFailureException.class, () -> integrationService.asyncObtainPriceCalculationDataFromExternalClients(request));

        Mockito.verify(vatRateClient).getVatRate(request.date());
        Mockito.verify(basePriceClient).getBaseRoutePrice(request.routeName(), request.date());
    }

    @Test
    void getExternalDataFailureBasePriceClientCallException() {
        // set-up
        Mockito.when(vatRateClient.getVatRate(request.date()))
                .thenReturn(CompletableFuture.completedFuture(vatRateResponse));
        Mockito.when(basePriceClient.getBaseRoutePrice(request.routeName(), request.date()))
                .thenReturn(CompletableFuture.failedFuture(new IntegrationFailureException("message")));

        //execute and verify
        assertThrows(IntegrationFailureException.class, () -> integrationService.asyncObtainPriceCalculationDataFromExternalClients(request));

        Mockito.verify(vatRateClient).getVatRate(request.date());
        Mockito.verify(basePriceClient).getBaseRoutePrice(request.routeName(), request.date());
    }
}