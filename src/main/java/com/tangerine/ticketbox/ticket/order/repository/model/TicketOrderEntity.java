package com.tangerine.ticketbox.ticket.order.repository.model;

import com.tangerine.ticketbox.ticket.order.vo.Email;
import com.tangerine.ticketbox.ticket.order.vo.TicketOrderStatus;

import java.time.LocalDate;
import java.util.UUID;

public record TicketOrderEntity(
        UUID orderId,
        Email email,
        LocalDate orderedAt,
        TicketOrderStatus ticketOrderStatus
) {

}
