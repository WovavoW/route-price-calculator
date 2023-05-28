package com.demo.route.price.calculator.service;

import com.demo.route.price.calculator.api.PriceCalculationFlowService;
import com.demo.route.price.calculator.exception.InternalServerException;
import com.demo.route.price.calculator.model.Ticket;
import com.demo.route.price.calculator.service.builder.TicketBundleBuilderService;
import com.demo.route.price.calculator.service.builder.TicketsBuilderService;
import com.demo.route.price.calculator.service.data.CalculatedPrices;
import com.demo.route.price.calculator.service.data.ExternalData;
import com.demo.route.price.calculator.service.integration.IntegrationService;
import com.demo.route.price.calculator.util.AbstractTest;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;

class PriceCalculationFlowServiceImplTest extends AbstractTest {

    private final ExternalData externalData = new ExternalData(
            BigDecimal.valueOf(21),
            BigDecimal.valueOf(10)
    );
    private final PriceCalculationFlowService priceCalculationFlowService;
    @Mock
    private IntegrationService integrationService;

    @Mock
    private BasePricesCalculationService basePricesCalculationService;

    @Mock
    private TicketsBuilderService ticketsBuilderService;

    @Mock
    private TicketBundleBuilderService ticketBundleBuilderService;

    public PriceCalculationFlowServiceImplTest() {
        MockitoAnnotations.openMocks(this);
        priceCalculationFlowService = new PriceCalculationFlowServiceImpl(integrationService, basePricesCalculationService, ticketsBuilderService, ticketBundleBuilderService);
    }

    @Test
    void successfulCalculationFlow() {
        // set-up
        final var request = getPriceCalculationRequest();
        final var tickets = List.of(
                new Ticket(
                        request.passengers().get(0),
                        null
                )
        );
        final var calculatedPrices = getCalculatedPrices();
        final var calculatedPricesCaptor = ArgumentCaptor.forClass(CalculatedPrices.class);
        Mockito.when(integrationService.asyncObtainPriceCalculationDataFromExternalClients(request))
                .thenReturn(externalData);
        Mockito.when(basePricesCalculationService
                .getBaseCalculatedPrices(
                        externalData.vatRate(),
                        externalData.basePrice()
                )
        ).thenReturn(calculatedPrices);
        Mockito.when(ticketsBuilderService.buildTicketsForPassengers(eq(request.passengers()), any()))
                .thenReturn(tickets);

        // execute
        priceCalculationFlowService.calculatePrices(request);

        //verify
        Mockito.verify(integrationService).asyncObtainPriceCalculationDataFromExternalClients(request);
        Mockito.verify(basePricesCalculationService).getBaseCalculatedPrices(
                externalData.vatRate(),
                externalData.basePrice()
        );
        Mockito.verify(ticketsBuilderService).buildTicketsForPassengers(eq(request.passengers()), calculatedPricesCaptor.capture());
        Mockito.verify(ticketBundleBuilderService).buildTicketBundleResponse(request.routeName(), tickets);

        var capturedCalculatedPrices = calculatedPricesCaptor.getValue();
        assertEquals(getFormattedBigDecimal(21.0), capturedCalculatedPrices.vatRate());
        assertEquals(getFormattedBigDecimal(12.10), capturedCalculatedPrices.adultTicketPriceWithVat());
        assertEquals(getFormattedBigDecimal(6.05), capturedCalculatedPrices.childTicketPriceWithVat());
        assertEquals(getFormattedBigDecimal(3.63), capturedCalculatedPrices.luggagePriceWithVat());
    }

    @Test
    void failedCalculationFlow() {
        // set-up
        final var request = getPriceCalculationRequest();
        Mockito.when(integrationService.asyncObtainPriceCalculationDataFromExternalClients(request))
                .thenReturn(externalData);
        Mockito.when(basePricesCalculationService
                .getBaseCalculatedPrices(
                        externalData.vatRate(),
                        externalData.basePrice()
                )
        ).thenThrow(new RuntimeException());

        // execute and verify
        assertThrows(InternalServerException.class, () -> priceCalculationFlowService.calculatePrices(request));

        Mockito.verify(integrationService).asyncObtainPriceCalculationDataFromExternalClients(request);
        Mockito.verify(basePricesCalculationService).getBaseCalculatedPrices(
                externalData.vatRate(),
                externalData.basePrice()
        );
        Mockito.verify(ticketsBuilderService, never()).buildTicketsForPassengers(any(), any());
        Mockito.verify(ticketBundleBuilderService, never()).buildTicketBundleResponse(any(), any());
    }
}
