package com.tangerine.theatre_manager.ticket.repository.dto;

import com.tangerine.theatre_manager.ticket.vo.Price;
import com.tangerine.theatre_manager.ticket.vo.Quantity;

import java.time.LocalDate;
import java.util.UUID;

public record TicketEntity(
        UUID ticketId,
        UUID orderId,
        UUID performanceId,
        Price ticketPrice,
        Quantity ticketQuantity,
        LocalDate reservedDate
) {
}
