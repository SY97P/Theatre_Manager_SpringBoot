package com.tangerine.ticketbox.ticket.repository;

import com.tangerine.ticketbox.ticket.model.Price;
import com.tangerine.ticketbox.ticket.model.Quantity;

import java.time.LocalDate;
import java.util.UUID;

public record Ticket(
        UUID ticketId,
        UUID theatreId,
        Price ticketPrice,
        Quantity ticketQuantity,
        LocalDate reservedDate
) {

}
