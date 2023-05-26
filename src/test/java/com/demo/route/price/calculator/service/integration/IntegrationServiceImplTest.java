package com.demo.route.price.calculator.service.integration;

import com.demo.route.price.calculator.client.BasePriceClient;
import com.demo.route.price.calculator.client.VatRateClient;
import com.demo.route.price.calculator.exception.IntegrationFailureException;
import com.demo.route.price.calculator.model.response.BasePriceServiceWrappedResponse;
import com.demo.route.price.calculator.model.response.VatRateServiceWrappedResponse;
import com.demo.route.price.calculator.util.AbstractTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IntegrationServiceImplTest extends AbstractTest {

    private final IntegrationService integrationService;
    LocalDate date = LocalDate.now();
    @Mock
    private BasePriceClient basePriceClient;
    @Mock
    private VatRateClient vatRateClient;

    public IntegrationServiceImplTest() {
        MockitoAnnotations.openMocks(this);
        integrationService = new IntegrationServiceImpl(basePriceClient, vatRateClient);
    }

    @Test
    void getVatRateSuccess() {
        // set-up
        Mockito.when(vatRateClient.getVatRate(date))
                .thenReturn(new VatRateServiceWrappedResponse(BigDecimal.valueOf(21)));

        //execute and verify
        assertDoesNotThrow(() -> integrationService.getVatRate(date));
    }

    @Test
    void getBasePriceSuccess() {
        // set-up
        Mockito.when(basePriceClient.getBaseRoutePrice(ROUTE_NAME, date))
                .thenReturn(new BasePriceServiceWrappedResponse(BigDecimal.valueOf(10)));

        //execute and verify
        assertDoesNotThrow(() -> integrationService.getBaseRoutePrice(ROUTE_NAME, date));
    }

    @Test
    void getVatRateClientException() {
        // set-up
        Mockito.when(vatRateClient.getVatRate(date))
                .thenThrow(new RuntimeException());

        //execute and verify
        assertThrows(IntegrationFailureException.class, () -> integrationService.getVatRate(date));
    }

    @Test
    void getBasePriceClientException() {
        // set-up
        Mockito.when(basePriceClient.getBaseRoutePrice(ROUTE_NAME, date))
                .thenThrow(new RuntimeException());

        //execute and verify
        assertThrows(IntegrationFailureException.class, () -> integrationService.getBaseRoutePrice(ROUTE_NAME, date));
    }

    @Test
    void getVatRateNullResponse() {
        // set-up
        Mockito.when(vatRateClient.getVatRate(date))
                .thenReturn(null);

        //execute and verify
        assertThrows(IntegrationFailureException.class, () -> integrationService.getVatRate(date));
    }

    @Test
    void getBasePriceNullResponse() {
        // set-up
        Mockito.when(basePriceClient.getBaseRoutePrice(ROUTE_NAME, date))
                .thenReturn(null);

        //execute and verify
        assertThrows(IntegrationFailureException.class, () -> integrationService.getBaseRoutePrice(ROUTE_NAME, date));
    }

}