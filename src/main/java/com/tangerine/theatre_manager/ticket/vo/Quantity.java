package com.tangerine.theatre_manager.ticket.vo;

import com.tangerine.theatre_manager.global.exception.InvalidDataException;

public record Quantity(long quantityValue) {
    public Quantity {
        validatePositive(quantityValue);
    }

    private void validatePositive(long quantityValue) {
        if (quantityValue < 0) {
            throw new InvalidDataException("Quantity value is lower than 0");
        }
    }
}
