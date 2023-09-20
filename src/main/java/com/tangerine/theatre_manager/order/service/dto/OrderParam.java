package com.tangerine.theatre_manager.order.service.dto;

import com.tangerine.theatre_manager.global.auth.Email;
import com.tangerine.theatre_manager.global.price.Price;
import com.tangerine.theatre_manager.order.model.Order;
import com.tangerine.theatre_manager.order.model.vo.OrderStatus;
import java.time.LocalDate;
import java.util.List;

public record OrderParam(
        Email email,
        OrderStatus orderStatus,
        List<TicketParam> tickets
) {

    public static Order to(OrderParam param) {
        return new Order(
                param.email,
                new Price(0L),
                param.orderStatus,
                LocalDate.now()
        );
    }
}
