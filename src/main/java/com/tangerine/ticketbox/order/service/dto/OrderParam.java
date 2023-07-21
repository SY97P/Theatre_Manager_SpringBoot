package com.tangerine.ticketbox.order.service.dto;

import com.tangerine.ticketbox.order.model.Email;
import com.tangerine.ticketbox.order.model.OrderStatus;

import java.time.LocalDate;
import java.util.UUID;

public record OrderParam(
        UUID orderId,
        Email email,
        LocalDate orderedAt,
        OrderStatus orderStatus
) {
}
