package com.demo.route.price.calculator.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.springframework.lang.Nullable;

@JsonDeserialize(builder = Ticket.Builder.class)
public record Ticket(
        Passenger passenger,
        Price price
) {
    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {
        @Nullable
        private Passenger passenger;
        @Nullable
        private Price price;

        public Builder passenger(Passenger passenger) {
            this.passenger = passenger;
            return this;
        }

        public Builder price(Price price) {
            this.price = price;
            return this;
        }

        public Ticket build() {
            return new Ticket(
                    passenger,
                    price
            );
        }
    }
}