package com.tangerine.theatre_manager.order.ticket.model;

import com.tangerine.theatre_manager.performance.vo.Price;

import java.time.LocalDate;
import java.util.UUID;

public record Ticket(
        UUID ticketId,
        UUID orderId,
        Price ticketPrice,
        LocalDate reservedDate
) {
}