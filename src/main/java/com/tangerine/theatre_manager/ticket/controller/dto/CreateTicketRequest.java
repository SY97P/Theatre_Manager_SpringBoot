package com.tangerine.theatre_manager.ticket.controller.dto;

import java.time.LocalDate;
import java.util.UUID;

public record CreateTicketRequest(
        UUID performanceId,
        long ticketPrice,
        long ticketQuantity,
        LocalDate reservedDate
) {
}
