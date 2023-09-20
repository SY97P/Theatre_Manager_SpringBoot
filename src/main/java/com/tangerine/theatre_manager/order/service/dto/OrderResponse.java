package com.tangerine.theatre_manager.order.service.dto;

import com.tangerine.theatre_manager.order.model.Order;
import java.time.LocalDate;
import java.util.List;

public record OrderResponse(
        Long id,
        String email,
        long totalPrice,
        String orderStatus,
        LocalDate orderDate,
        List<TicketResponse> tickets
) {

    public static OrderResponse of(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getEmailAddress(),
                order.getTotalPriceValue(),
                order.getOrderStatusName(),
                order.getOrderDate(),
                order.getTickets().stream()
                        .map(TicketResponse::of)
                        .toList()
        );
    }
}
