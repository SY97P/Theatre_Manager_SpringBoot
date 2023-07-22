package com.tangerine.theatre_manager.ticket.order.controller.dto;

import com.tangerine.theatre_manager.ticket.controller.dto.CreateTicketRequest;

import java.util.List;

public record CreateTicketOrderRequest(
        String email,
        List<CreateTicketRequest> tickets
) {
}
