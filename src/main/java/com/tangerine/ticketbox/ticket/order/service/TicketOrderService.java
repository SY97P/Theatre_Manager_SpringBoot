package com.tangerine.ticketbox.ticket.order.service;

import com.tangerine.ticketbox.ticket.order.service.dto.TicketOrderParam;
import com.tangerine.ticketbox.ticket.order.service.dto.TicketOrderResult;
import com.tangerine.ticketbox.ticket.order.vo.Email;

import java.util.List;
import java.util.UUID;

public interface TicketOrderService {

    void createOrder(TicketOrderParam param);

    void updateOrder(TicketOrderParam param);

    void deleteAllOrders();

    void deleteOrderById(UUID orderId);

    List<TicketOrderResult> findAllOrders();

    TicketOrderResult findOrderById(UUID orderId);

    TicketOrderResult findOrderByEmail(Email email);

}
