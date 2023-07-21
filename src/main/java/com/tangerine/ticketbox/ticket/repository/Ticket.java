package com.tangerine.ticketbox.ticket.repository;

import com.tangerine.ticketbox.ticket.model.Price;
import com.tangerine.ticketbox.ticket.model.Quantity;

import java.time.LocalDate;
import java.util.UUID;

// todo orderId 필드 추가
public record Ticket(
        UUID ticketId,
        UUID theatreId,
        Price ticketPrice,
        Quantity ticketQuantity,
        LocalDate reservedDate
) {

}
