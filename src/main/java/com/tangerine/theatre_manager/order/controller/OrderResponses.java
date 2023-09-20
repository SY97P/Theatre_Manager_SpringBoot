package com.tangerine.theatre_manager.order.controller;

import com.tangerine.theatre_manager.order.model.Order;
import com.tangerine.theatre_manager.order.service.dto.OrderResponse;
import org.springframework.data.domain.Page;

public record OrderResponses(
        Page<OrderResponse> responses
) {

    public static OrderResponses of(Page<Order> responses) {
        return new OrderResponses(responses.map(OrderResponse::of));
    }

}
