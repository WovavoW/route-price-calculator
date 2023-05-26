package com.demo.route.price.calculator.model.request;


import com.demo.route.price.calculator.model.Passenger;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@JsonDeserialize(builder = PriceCalculationRequest.Builder.class)
public record PriceCalculationRequest(
        @NotBlank(message = "routeName is required.")
        @Pattern(regexp = "^\\w{1,15}-\\w{1,15}$", flags = {Pattern.Flag.CASE_INSENSITIVE}, message = "routeName is invalid.")
        String routeName,
        @NotNull(message = "date is required.")
        LocalDate date,
        @NotEmpty(message = "at least one passenger should be present")
        @Valid
        List<Passenger> passengers
) {
    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {
        @Nullable
        LocalDate date;
        @Nullable
        private String routeName;
        @Nullable
        private List<Passenger> passengers;

        public Builder routeName(String routeName) {
            this.routeName = routeName;
            return this;
        }

        public Builder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public Builder passengers(List<Passenger> passengers) {
            this.passengers = Collections.unmodifiableList(passengers);
            return this;
        }

        public PriceCalculationRequest build() {
            return new PriceCalculationRequest(routeName, date, passengers);
        }
    }
}