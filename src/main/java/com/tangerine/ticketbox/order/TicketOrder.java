package com.tangerine.ticketbox.order;

import java.time.LocalDate;
import java.util.UUID;

public class TicketOrder {

    private final UUID ticketOrderId;
    private final Email email;
    private final LocalDate createdAt;
    private final OrderStatus orderStatus;

    public TicketOrder(UUID ticketOrderId, Email email, LocalDate createdAt, OrderStatus orderStatus) {
        this.ticketOrderId = ticketOrderId;
        this.email = email;
        this.createdAt = createdAt;
        this.orderStatus = orderStatus;
    }
}
