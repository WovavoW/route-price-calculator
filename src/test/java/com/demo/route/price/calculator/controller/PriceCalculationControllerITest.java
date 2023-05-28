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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.stream.Stream;

import static com.demo.route.price.calculator.interceptor.ApiVersionInterceptor.ACCEPT_VERSION_HEADER;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PriceCalculationControllerITest extends AbstractTest {
    private final MockMvc mvc;
    private final String apiVersion;
    private WireMockServer wireMockServer;

    public PriceCalculationControllerITest(@Autowired MockMvc mvc, @Value("${api.version}") String apiVersion) {
        this.mvc = mvc;
        this.apiVersion = apiVersion;
    }

    static Stream<TestArguments> testInput() {
        return Stream.of(
                new TestArguments("price-calculation-request.json", "price-calculation-response.json", 200, true),
                new TestArguments("price-calculation-request-vat-rate-request-fail.json", null, 500, true),
                new TestArguments("price-calculation-request-base-price-request-fail.json", null, 500, true),
                new TestArguments("price-calculation-request.json", null, 400, false)
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
                        .header(ACCEPT_VERSION_HEADER, arguments.correctApiVersion ? apiVersion : "invalid")
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
            int responseCode,
            boolean correctApiVersion
    ) {

    }
}