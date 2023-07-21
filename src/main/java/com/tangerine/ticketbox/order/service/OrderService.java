package com.tangerine.ticketbox.order.service;

import com.tangerine.ticketbox.order.model.Email;
import com.tangerine.ticketbox.order.service.dto.OrderParam;
import com.tangerine.ticketbox.order.service.dto.OrderResult;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    void createOrder(OrderParam param);

    void updateOrder(OrderParam param);

    void deleteAllOrders();

    void deleteOrderById(UUID orderId);

    List<OrderResult> findAllOrders();

    OrderResult findOrderById(UUID orderId);

    OrderResult findOrderByEmail(Email email);

}
