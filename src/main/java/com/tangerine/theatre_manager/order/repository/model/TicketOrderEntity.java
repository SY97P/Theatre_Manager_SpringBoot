package com.tangerine.theatre_manager.order.repository.model;

import com.tangerine.theatre_manager.order.vo.Email;
import com.tangerine.theatre_manager.order.vo.TicketOrderStatus;

import java.time.LocalDate;
import java.util.UUID;

public record TicketOrderEntity(
        UUID orderId,
        Email email,
        LocalDate orderedAt,
        TicketOrderStatus ticketOrderStatus
) {
}
