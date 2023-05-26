package com.demo.route.price.calculator.client.stub;

import com.demo.route.price.calculator.client.BasePriceClient;
import com.demo.route.price.calculator.model.response.BasePriceServiceWrappedResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@Profile("stub")
public class BasePriceClientStub implements BasePriceClient {

    public BasePriceServiceWrappedResponse getBaseRoutePrice(
            String routeName,
            final LocalDate date
    ) {
        return new BasePriceServiceWrappedResponse(
                BigDecimal.valueOf(10)
        );
    }
}