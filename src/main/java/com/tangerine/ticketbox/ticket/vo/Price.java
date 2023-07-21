package com.tangerine.ticketbox.ticket.vo;

import com.tangerine.ticketbox.global.exception.InvalidDataException;

public record Price(long priceValue) {
    public Price {
        validatePositive(priceValue);
    }

    private void validatePositive(long priceValue) {
        if (priceValue < 0) {
            throw new InvalidDataException("Price value is lower than 0");
        }
    }
}
