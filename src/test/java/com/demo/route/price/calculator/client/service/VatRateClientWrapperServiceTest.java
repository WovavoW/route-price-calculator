package com.demo.route.price.calculator.client.service;

import com.demo.route.price.calculator.client.VatRateClient;
import com.demo.route.price.calculator.exception.IntegrationFailureException;
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

class VatRateClientWrapperServiceTest extends AbstractTest {

    private final VatRateClientWrapperService vatRateClientWrapperService;
    LocalDate date = LocalDate.now();

    @Mock
    private VatRateClient vatRateClient;

    public VatRateClientWrapperServiceTest() {
        MockitoAnnotations.openMocks(this);
        vatRateClientWrapperService = new VatRateClientWrapperService(vatRateClient);
    }

    @Test
    void getVatRateSuccess() {
        // set-up
        Mockito.when(vatRateClient.getVatRate(date))
                .thenReturn(new VatRateServiceWrappedResponse(BigDecimal.valueOf(21)));

        //execute and verify
        assertDoesNotThrow(() -> vatRateClientWrapperService.getVatRate(date));
    }

    @Test
    void getVatRateClientException() {
        // set-up
        Mockito.when(vatRateClient.getVatRate(date))
                .thenThrow(new RuntimeException());

        //execute and verify
        assertThrows(IntegrationFailureException.class, () -> vatRateClientWrapperService.getVatRate(date));
    }

    @Test
    void getVatRateNullResponse() {
        // set-up
        Mockito.when(vatRateClient.getVatRate(date))
                .thenReturn(null);

        //execute and verify
        assertThrows(IntegrationFailureException.class, () -> vatRateClientWrapperService.getVatRate(date));
    }

}