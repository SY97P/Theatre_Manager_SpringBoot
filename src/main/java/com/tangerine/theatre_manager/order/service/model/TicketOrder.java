package com.tangerine.theatre_manager.order.service.model;

import com.tangerine.theatre_manager.order.ticket.model.Ticket;
import com.tangerine.theatre_manager.order.vo.Email;
import com.tangerine.theatre_manager.order.vo.TicketOrderStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record TicketOrder(
        UUID orderId,
        Email email,
        LocalDate orderedAt,
        TicketOrderStatus ticketOrderStatus,
        List<Ticket> tickets
) {

}
