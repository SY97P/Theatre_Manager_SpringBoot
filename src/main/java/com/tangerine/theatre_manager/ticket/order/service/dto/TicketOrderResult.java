package com.tangerine.theatre_manager.ticket.order.service.dto;

import com.tangerine.theatre_manager.ticket.order.vo.Email;
import com.tangerine.theatre_manager.ticket.order.vo.TicketOrderStatus;
import com.tangerine.theatre_manager.ticket.service.model.Ticket;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record TicketOrderResult(
        UUID orderId,
        Email email,
        LocalDate orderedAt,
        TicketOrderStatus ticketOrderStatus,
        List<Ticket> tickets
) {
}
