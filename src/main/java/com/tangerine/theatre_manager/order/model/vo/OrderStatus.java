package com.tangerine.theatre_manager.order.model.vo;

import com.tangerine.theatre_manager.global.exception.OrderException;

public enum OrderStatus {
    ACCEPTED,
    PAYMENT_DONE,
    OUTDATED;

    public OrderStatus getNextStatus() {
        try {
            return OrderStatus.values()[this.ordinal() + 1];
        } catch (ArrayIndexOutOfBoundsException exception) {
            throw new OrderException();
        }
    }
}
