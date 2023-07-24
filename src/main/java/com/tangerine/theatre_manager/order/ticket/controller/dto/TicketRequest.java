package com.tangerine.theatre_manager.order.ticket.controller.dto;

import java.time.LocalDate;
import java.util.UUID;

public record TicketRequest(
        UUID orderId,
        long ticketPrice,
        LocalDate reservedDate
) {
}
