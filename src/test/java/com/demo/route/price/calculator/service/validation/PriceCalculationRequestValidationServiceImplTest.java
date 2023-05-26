package com.demo.route.price.calculator.service.validation;

import com.demo.route.price.calculator.exception.InvalidRequestException;
import com.demo.route.price.calculator.model.request.PriceCalculationRequest;
import com.demo.route.price.calculator.service.validation.validator.RequestValidator;
import com.demo.route.price.calculator.service.validation.validator.ValidationResult;
import com.demo.route.price.calculator.util.AbstractTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PriceCalculationRequestValidationServiceImplTest extends AbstractTest {

    private final List<RequestValidator<PriceCalculationRequest>> validators;
    private final PriceCalculationRequestValidationServiceImpl validationService;
    @Mock
    private RequestValidator<PriceCalculationRequest> validatorOne;
    @Mock
    private RequestValidator<PriceCalculationRequest> validatorTwo;

    public PriceCalculationRequestValidationServiceImplTest() {
        MockitoAnnotations.openMocks(this);
        validators = List.of(validatorOne, validatorTwo);
        validationService = new PriceCalculationRequestValidationServiceImpl(validators);
    }

    @Test
    void allValidatorsPass() {
        // set-up
        final PriceCalculationRequest request = getPriceCalculationRequest();

        Mockito.when(validatorOne.validate(request)).thenReturn(success());
        Mockito.when(validatorTwo.validate(request)).thenReturn(success());

        //execute and verify
        assertDoesNotThrow(() -> validationService.validate(request));
        verifyMockCalls(request);
    }

    @Test
    void oneValidatorFail() {
        // set-up
        final PriceCalculationRequest request = getPriceCalculationRequest();

        Mockito.when(validatorOne.validate(request)).thenReturn(success());
        Mockito.when(validatorTwo.validate(request)).thenReturn(failure());

        //execute and verify
        Throwable exception = assertThrows(InvalidRequestException.class, () -> validationService.validate(request));

        String expectedMessage = "Request is invalid due to following errors: \nerror";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
        verifyMockCalls(request);
    }

    @Test
    void allValidatorsFail() {
        // set-up
        final PriceCalculationRequest request = getPriceCalculationRequest();

        Mockito.when(validatorOne.validate(request)).thenReturn(failure());
        Mockito.when(validatorTwo.validate(request)).thenReturn(failure());

        //execute and verify
        Throwable exception = assertThrows(InvalidRequestException.class, () -> validationService.validate(request));

        String expectedMessage = "Request is invalid due to following errors: \nerror\nerror";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
        verifyMockCalls(request);
    }

    private void verifyMockCalls(PriceCalculationRequest request) {
        Mockito.verify(validatorOne).validate(request);
        Mockito.verify(validatorTwo).validate(request);
    }

    private ValidationResult success() {
        return new ValidationResult(true, null);
    }

    private ValidationResult failure() {
        return new ValidationResult(false, "error");
    }
}