package com.tangerine.ticketbox.ticket.controller.dto;

import java.time.LocalDate;
import java.util.UUID;

public record CreateTicketRequest(
        UUID theatreId,
        long ticketPrice,
        long ticketQuantity,
        LocalDate reservedDate
) {
}
