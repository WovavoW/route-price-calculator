package com.demo.route.price.calculator.service.validation.validator;

import com.demo.route.price.calculator.model.enumerated.AgeGroup;
import com.demo.route.price.calculator.model.request.PriceCalculationRequest;
import com.demo.route.price.calculator.util.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PassengerNumberValidatorTest extends AbstractTest {

    private final PassengerNumberValidator passengerNumberValidator = new PassengerNumberValidator();

    @Test
    void distinctPassengerNumbers() {
        //set-up
        var passengers = List.of(
                getPassenger(1, AgeGroup.ADULT, 2),
                getPassenger(2, AgeGroup.CHILD, 1)
        );
        var request = new PriceCalculationRequest.Builder().passengers(passengers).build();

        // execute
        var validationResult = passengerNumberValidator.validate(request);

        // evaluate
        assertTrue(validationResult.result());
        assertNull(validationResult.errorMessage());
    }

    @Test
    void samePassengerNumbers() {
        //set-up
        var passengers = List.of(
                getPassenger(1, AgeGroup.ADULT, 2),
                getPassenger(2, AgeGroup.CHILD, 1),
                getPassenger(2, AgeGroup.ADULT, 0)
        );
        var request = new PriceCalculationRequest.Builder().passengers(passengers).build();

        // execute
        var validationResult = passengerNumberValidator.validate(request);

        // evaluate
        assertFalse(validationResult.result());
        assertNotNull(validationResult.errorMessage());
    }

}