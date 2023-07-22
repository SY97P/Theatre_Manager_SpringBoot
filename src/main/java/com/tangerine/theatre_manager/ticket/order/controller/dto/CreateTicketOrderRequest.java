package com.tangerine.theatre_manager.ticket.order.controller.dto;

import com.tangerine.theatre_manager.ticket.controller.dto.CreateTicketRequest;
import com.tangerine.theatre_manager.ticket.order.vo.Email;

import java.util.List;

public record CreateTicketOrderRequest(
        Email email,
        List<CreateTicketRequest> tickets
) {
}
