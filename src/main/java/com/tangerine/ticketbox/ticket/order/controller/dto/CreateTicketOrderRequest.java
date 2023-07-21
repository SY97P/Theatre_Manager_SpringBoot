package com.tangerine.ticketbox.ticket.order.controller.dto;

import com.tangerine.ticketbox.ticket.order.vo.Email;
import com.tangerine.ticketbox.ticket.service.model.Ticket;

import java.util.List;

public record CreateTicketOrderRequest(
        Email email,
        List<Ticket> tickets
) {
}
