package com.tangerine.ticketbox.ticket.order.controller.dto;

import com.tangerine.ticketbox.ticket.controller.dto.UpdateTicketRequest;
import com.tangerine.ticketbox.ticket.order.vo.Email;
import com.tangerine.ticketbox.ticket.order.vo.TicketOrderStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record UpdateTicketOrderRequest(
        UUID orderId,
        Email email,
        LocalDate orderedAt,
        TicketOrderStatus ticketOrderStatus,
        List<UpdateTicketRequest> tickets
) {
}
