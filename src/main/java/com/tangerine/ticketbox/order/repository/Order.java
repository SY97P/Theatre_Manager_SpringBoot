package com.tangerine.ticketbox.order.repository;

import com.tangerine.ticketbox.order.model.Email;
import com.tangerine.ticketbox.order.model.OrderStatus;

import java.time.LocalDate;
import java.util.UUID;

public record Order(
        UUID orderId,
        Email email,
        LocalDate orderedAt,
        OrderStatus orderStatus
) {

}
