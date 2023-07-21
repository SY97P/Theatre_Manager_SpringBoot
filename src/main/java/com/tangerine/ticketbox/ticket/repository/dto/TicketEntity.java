package com.tangerine.ticketbox.ticket.repository.dto;

import com.tangerine.ticketbox.ticket.vo.Price;
import com.tangerine.ticketbox.ticket.vo.Quantity;

import java.time.LocalDate;
import java.util.UUID;

public record TicketEntity(
        UUID ticketId,
        UUID orderId,
        UUID theatreId,
        Price ticketPrice,
        Quantity ticketQuantity,
        LocalDate reservedDate
) {
}
