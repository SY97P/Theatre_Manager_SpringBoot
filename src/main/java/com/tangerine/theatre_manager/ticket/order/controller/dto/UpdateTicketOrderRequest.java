package com.tangerine.theatre_manager.ticket.order.controller.dto;

import com.tangerine.theatre_manager.ticket.controller.dto.UpdateTicketRequest;
import com.tangerine.theatre_manager.ticket.order.vo.Email;
import com.tangerine.theatre_manager.ticket.order.vo.TicketOrderStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record UpdateTicketOrderRequest(
        UUID orderId,
        String email,
        LocalDate orderedAt,
        TicketOrderStatus ticketOrderStatus,
        List<UpdateTicketRequest> tickets
) {
}
