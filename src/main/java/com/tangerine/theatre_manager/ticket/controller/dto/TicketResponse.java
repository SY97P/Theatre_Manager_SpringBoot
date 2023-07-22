package com.tangerine.theatre_manager.ticket.controller.dto;

import java.time.LocalDate;
import java.util.UUID;

public record TicketResponse(
        UUID ticketId,
        UUID orderId,
        UUID performanceId,
        long ticketPrice,
        LocalDate reservedDate
) {
}
