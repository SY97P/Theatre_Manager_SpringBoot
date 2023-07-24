package com.tangerine.theatre_manager.order.controller.dto;

import com.tangerine.theatre_manager.order.ticket.controller.dto.TicketRequest;

import java.util.List;
import java.util.UUID;

public record TicketOrderRequest(
        UUID orderId,
        String email,
        List<TicketRequest> tickets
) {
}
