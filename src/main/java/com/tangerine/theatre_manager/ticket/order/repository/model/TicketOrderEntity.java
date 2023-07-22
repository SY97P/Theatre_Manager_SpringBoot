package com.tangerine.theatre_manager.ticket.order.repository.model;

import com.tangerine.theatre_manager.ticket.order.vo.Email;
import com.tangerine.theatre_manager.ticket.order.vo.TicketOrderStatus;

import java.time.LocalDate;
import java.util.UUID;

public record TicketOrderEntity(
        UUID orderId,
        Email email,
        LocalDate orderedAt,
        TicketOrderStatus ticketOrderStatus
) {

}
