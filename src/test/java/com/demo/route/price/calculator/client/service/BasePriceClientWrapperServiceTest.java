package com.demo.route.price.calculator.client.service;

import com.demo.route.price.calculator.client.BasePriceClient;
import com.demo.route.price.calculator.exception.IntegrationFailureException;
import com.demo.route.price.calculator.model.response.BasePriceServiceWrappedResponse;
import com.demo.route.price.calculator.util.AbstractTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BasePriceClientWrapperServiceTest extends AbstractTest {

    private final BasePriceClientWrapperService basePriceClientWrapperService;
    LocalDate date = LocalDate.now();
    @Mock
    private BasePriceClient basePriceClient;

    public BasePriceClientWrapperServiceTest() {
        MockitoAnnotations.openMocks(this);
        basePriceClientWrapperService = new BasePriceClientWrapperService(basePriceClient);
    }

    @Test
    void getBasePriceSuccess() {
        // set-up
        Mockito.when(basePriceClient.getBaseRoutePrice(ROUTE_NAME, date))
                .thenReturn(new BasePriceServiceWrappedResponse(BigDecimal.valueOf(10)));

        //execute and verify
        assertDoesNotThrow(() -> basePriceClientWrapperService.getBaseRoutePrice(ROUTE_NAME, date));
    }

    @Test
    void getBasePriceClientException() {
        // set-up
        Mockito.when(basePriceClient.getBaseRoutePrice(ROUTE_NAME, date))
                .thenThrow(new RuntimeException());

        //execute and verify
        assertThrows(IntegrationFailureException.class, () -> basePriceClientWrapperService.getBaseRoutePrice(ROUTE_NAME, date));
    }

    @Test
    void getBasePriceNullResponse() {
        // set-up
        Mockito.when(basePriceClient.getBaseRoutePrice(ROUTE_NAME, date))
                .thenReturn(null);

        //execute and verify
        assertThrows(IntegrationFailureException.class, () -> basePriceClientWrapperService.getBaseRoutePrice(ROUTE_NAME, date));
    }
}