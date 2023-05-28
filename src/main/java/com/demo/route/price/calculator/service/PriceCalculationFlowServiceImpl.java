package com.demo.route.price.calculator.service;

import com.demo.route.price.calculator.api.PriceCalculationFlowService;
import com.demo.route.price.calculator.exception.InternalServerException;
import com.demo.route.price.calculator.model.Ticket;
import com.demo.route.price.calculator.model.request.PriceCalculationRequest;
import com.demo.route.price.calculator.model.response.TicketBundleResponse;
import com.demo.route.price.calculator.service.builder.TicketBundleBuilderService;
import com.demo.route.price.calculator.service.builder.TicketsBuilderService;
import com.demo.route.price.calculator.service.data.CalculatedPrices;
import com.demo.route.price.calculator.service.integration.IntegrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PriceCalculationFlowServiceImpl implements PriceCalculationFlowService {

    private final Logger logger = LoggerFactory.getLogger(PriceCalculationFlowServiceImpl.class);

    private final IntegrationService integrationService;
    private final BasePricesCalculationService basePricesCalculationService;
    private final TicketsBuilderService ticketsBuilderService;
    private final TicketBundleBuilderService ticketBundleBuilderService;

    public PriceCalculationFlowServiceImpl(
            IntegrationService integrationService,
            BasePricesCalculationService basePricesCalculationService,
            TicketsBuilderService ticketsBuilderService,
            TicketBundleBuilderService ticketBundleBuilderService
    ) {
        this.integrationService = integrationService;
        this.basePricesCalculationService = basePricesCalculationService;
        this.ticketsBuilderService = ticketsBuilderService;
        this.ticketBundleBuilderService = ticketBundleBuilderService;
    }

    @Override
    public TicketBundleResponse calculatePrices(PriceCalculationRequest request) {
        var data = integrationService.asyncObtainPriceCalculationDataFromExternalClients(request);

        return calculatePrices(request, data.vatRate(), data.basePrice());
    }

    private TicketBundleResponse calculatePrices(
            PriceCalculationRequest request,
            BigDecimal vatRate,
            BigDecimal basePrice
    ) {
        try {
            final CalculatedPrices calculatedPrices = basePricesCalculationService
                    .getBaseCalculatedPrices(vatRate, basePrice);

            final List<Ticket> tickets = ticketsBuilderService
                    .buildTicketsForPassengers(request.passengers(), calculatedPrices);

            return ticketBundleBuilderService.buildTicketBundleResponse(request.routeName(), tickets);
        } catch (Exception e) {
            logger.error(String.format("Price calculation failed for request [%s]", request), e);
            throw new InternalServerException("Price calculation failed");
        }
    }
}
