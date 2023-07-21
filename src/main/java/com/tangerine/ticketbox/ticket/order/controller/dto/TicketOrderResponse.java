package com.tangerine.ticketbox.ticket.order.controller.dto;

import com.tangerine.ticketbox.ticket.order.vo.Email;
import com.tangerine.ticketbox.ticket.order.vo.TicketOrderStatus;
import com.tangerine.ticketbox.ticket.service.model.Ticket;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record TicketOrderResponse(
        UUID orderId,
        Email email,
        LocalDate orderedAt,
        TicketOrderStatus ticketOrderStatus,
        List<Ticket> tickets
) {
}
