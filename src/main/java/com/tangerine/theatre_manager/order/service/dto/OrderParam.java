package com.tangerine.theatre_manager.order.service.dto;

import com.tangerine.theatre_manager.global.price.Price;
import com.tangerine.theatre_manager.order.model.Order;
import com.tangerine.theatre_manager.order.model.vo.OrderStatus;
import com.tangerine.theatre_manager.user.model.User;
import java.time.LocalDate;
import java.util.List;

public record OrderParam(
        OrderStatus orderStatus,
        List<TicketParam> tickets
) {

    public static Order to(User user, OrderParam param) {
        return new Order(
                user,
                new Price(0L),
                param.orderStatus,
                LocalDate.now()
        );
    }
}
