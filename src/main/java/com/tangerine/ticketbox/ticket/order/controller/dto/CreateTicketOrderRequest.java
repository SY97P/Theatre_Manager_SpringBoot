package com.tangerine.ticketbox.ticket.order.controller.dto;

import com.tangerine.ticketbox.ticket.controller.dto.CreateTicketRequest;
import com.tangerine.ticketbox.ticket.order.vo.Email;

import java.util.List;

public record CreateTicketOrderRequest(
        Email email,
        List<CreateTicketRequest> tickets
) {
}
