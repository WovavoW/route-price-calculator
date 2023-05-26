package com.demo.route.price.calculator.controller;

import com.demo.route.price.calculator.mock.BasePriceServiceMock;
import com.demo.route.price.calculator.mock.VatRateServiceMock;
import com.demo.route.price.calculator.util.AbstractTest;
import com.demo.route.price.calculator.util.ResourceHelper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.stream.Stream;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PriceCalculationControllerITest extends AbstractTest {

    @Autowired
    private MockMvc mvc;
    private WireMockServer wireMockServer;

    static Stream<TestArguments> testInput() {
        return Stream.of(
                new TestArguments("price-calculation-request.json", "price-calculation-response.json", 200),
                new TestArguments("price-calculation-request-vat-rate-request-fail.json", null, 500),
                new TestArguments("price-calculation-request-base-price-request-fail.json", null, 500)
        );
    }

    @BeforeEach
    void setUp() {
        wireMockServer = new WireMockServer(9561);
        wireMockServer.start();
        configureFor("localhost", 9561);
        BasePriceServiceMock.setupMockResponses(wireMockServer);
        VatRateServiceMock.setupMockResponses(wireMockServer);
    }

    @AfterEach
    public void teardown() {
        wireMockServer.stop();
    }

    @ParameterizedTest
    @MethodSource("testInput")
    void testPriceControllerPost(
            TestArguments arguments
    ) throws Exception {
        // set-up
        final String requestBody = ResourceHelper.readFileFromResources(String.format("integration/input/%s", arguments.inputFile));
        String expectedResponse = null;
        if (arguments.responseFile != null) {
            expectedResponse = ResourceHelper.readFileFromResources(String.format("integration/output/%s", arguments.responseFile));
        }
        // execute and verify
        String actualResponse = mvc.perform(MockMvcRequestBuilders.post("/price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is(arguments.responseCode))
                .andReturn()
                .getResponse()
                .getContentAsString();

        if (expectedResponse != null) {
            JSONAssert.assertEquals(expectedResponse, actualResponse, true);
        }
    }

    record TestArguments(
            String inputFile,
            String responseFile,
            int responseCode
    ) {

    }
}