package com.demo.route.price.calculator.model;


import com.demo.route.price.calculator.model.enumerated.AgeGroup;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.lang.Nullable;

@JsonDeserialize(builder = Passenger.Builder.class)
public record Passenger(
        @NotNull(message = "number is required.")
        Integer number,
        @NotBlank(message = "name is required.")
        String name,
        @NotBlank(message = "surname is required.")
        String surname,
        @NotNull(message = "ageGroup is required.")
        AgeGroup ageGroup,
        @NotNull(message = "luggageItems is required.")
        @PositiveOrZero(message = "luggageItems should be positive or zero.")
        Integer luggageItems
) {
    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {
        @Nullable
        Integer number;
        @Nullable
        private String name;
        @Nullable
        private String surname;
        @Nullable
        private AgeGroup ageGroup;
        @Nullable
        private Integer luggageItems;

        public Builder number(Integer number) {
            this.number = number;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder surname(String surname) {
            this.surname = surname;
            return this;
        }

        public Builder ageGroup(AgeGroup ageGroup) {
            this.ageGroup = ageGroup;
            return this;
        }

        public Builder luggageItems(Integer luggageItems) {
            this.luggageItems = luggageItems;
            return this;
        }

        public Passenger build() {
            return new Passenger(number, name, surname, ageGroup, luggageItems);
        }
    }
}