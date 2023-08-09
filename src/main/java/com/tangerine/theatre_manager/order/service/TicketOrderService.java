package com.tangerine.theatre_manager.order.service;

import com.tangerine.theatre_manager.order.service.model.TicketOrder;
import com.tangerine.theatre_manager.order.vo.Email;

import java.util.UUID;

public interface TicketOrderService {

    UUID createOrder(TicketOrder param);

    UUID deleteOrderById(UUID orderId);

    TicketOrder findOrderById(UUID orderId);

    TicketOrder findOrderByEmail(Email email);

}
