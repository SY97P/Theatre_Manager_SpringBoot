package com.tangerine.ticketbox.ticket.service.model;

import com.tangerine.ticketbox.ticket.vo.Price;
import com.tangerine.ticketbox.ticket.vo.Quantity;

import java.time.LocalDate;
import java.util.UUID;

public record Ticket(
        UUID ticketId,
        UUID orderId,
        UUID theatreId,
        Price ticketPrice,
        Quantity ticketQuantity,
        LocalDate reservedDate
) {

}
