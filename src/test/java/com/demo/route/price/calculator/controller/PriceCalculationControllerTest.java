package com.demo.route.price.calculator.controller;

import com.demo.route.price.calculator.api.PriceCalculationFlowService;
import com.demo.route.price.calculator.api.PriceCalculationRequestValidationService;
import com.demo.route.price.calculator.model.response.TicketBundleResponse;
import com.demo.route.price.calculator.util.AbstractTest;
import com.demo.route.price.calculator.util.ResourceHelper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.verification.VerificationMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PriceCalculationController.class)
class PriceCalculationControllerTest extends AbstractTest {

    private final TicketBundleResponse response = getTicketBundle();
    @Autowired
    private MockMvc mvc;
    @MockBean
    private PriceCalculationFlowService businessService;
    @MockBean
    private PriceCalculationRequestValidationService validationService;

    static Stream<TestArguments> testInput() {
        return Stream.of(
                new TestArguments("price-calculation-request.json", 200, times(1)),
                new TestArguments("price-calculation-request-absent-passengers.json", 400, never()),
                new TestArguments("price-calculation-request-blank-route.json", 400, never()),
                new TestArguments("price-calculation-request-date-null.json", 400, never()),
                new TestArguments("price-calculation-request-invalid-route.json", 400, never()),
                new TestArguments("price-calculation-request-passenger-blank-name.json", 400, never()),
                new TestArguments("price-calculation-request-passenger-blank-surname.json", 400, never()),
                new TestArguments("price-calculation-request-passenger-missing-age.json", 400, never()),
                new TestArguments("price-calculation-request-passenger-missing-luggage-items.json", 400, never()),
                new TestArguments("price-calculation-request-passenger-missing-number.json", 400, never()),
                new TestArguments("price-calculation-request-passenger-negative-luggage-items.json", 400, never())
        );
    }

    @ParameterizedTest
    @MethodSource("testInput")
    void testPriceControllerPost(
            TestArguments arguments
    ) throws Exception {
        // set-up
        doNothing().when(validationService).validate(any());
        when(businessService.calculatePrices(any())).thenReturn(response);

        String requestBody = ResourceHelper.readFileFromResources(String.format("controller/input/%s", arguments.inputFile));

        // execute and verify
        mvc.perform(MockMvcRequestBuilders.post("/price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is(arguments.responseCode));

        verify(validationService, arguments.mockCallTimes).validate(any());
        verify(businessService, arguments.mockCallTimes).calculatePrices(any());
    }

    record TestArguments(
            String inputFile,
            int responseCode,
            VerificationMode mockCallTimes
    ) {

    }
}