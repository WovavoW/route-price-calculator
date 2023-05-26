package com.demo.route.price.calculator.model.response;

import com.demo.route.price.calculator.model.Ticket;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@JsonDeserialize(builder = TicketBundleResponse.Builder.class)
public record TicketBundleResponse(
        String routeName,
        List<Ticket> tickets,
        BigDecimal totalPrice
) {
    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {
        @Nullable
        String routeName;
        @Nullable
        List<Ticket> tickets;
        @Nullable
        BigDecimal totalPrice;

        public Builder routeName(String routeName) {
            this.routeName = routeName;
            return this;
        }

        public Builder tickets(List<Ticket> tickets) {
            this.tickets = Collections.unmodifiableList(tickets);
            return this;
        }

        public Builder totalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public TicketBundleResponse build() {
            return new TicketBundleResponse(
                    routeName,
                    tickets,
                    totalPrice
            );
        }
    }
}
