package com.demo.route.price.calculator.client;

import com.demo.route.price.calculator.model.response.BasePriceServiceWrappedResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@FeignClient(name = "base-price-client", url = "${base-price.service.url}", path = "/base-price")
public interface BasePriceClient {

    @GetMapping
    BasePriceServiceWrappedResponse getBaseRoutePrice(
            @RequestParam(name = "routeName") String routeName,
            @RequestParam(name = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate date
    );

}
