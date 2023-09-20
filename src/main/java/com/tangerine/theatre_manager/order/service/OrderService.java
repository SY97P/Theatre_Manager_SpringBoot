package com.tangerine.theatre_manager.order.service;

import com.tangerine.theatre_manager.global.exception.OrderException;
import com.tangerine.theatre_manager.order.model.Order;
import com.tangerine.theatre_manager.order.model.Ticket;
import com.tangerine.theatre_manager.order.repository.OrderRepository;
import com.tangerine.theatre_manager.order.repository.TicketRepository;
import com.tangerine.theatre_manager.order.service.dto.OrderResponse;
import com.tangerine.theatre_manager.order.service.dto.TicketResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final TicketRepository ticketRepository;

    public OrderService(OrderRepository orderRepository, TicketRepository ticketRepository) {
        this.orderRepository = orderRepository;
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public OrderResponse editOrderStatus(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException())
                .changeNextOrderStatus();
        return OrderResponse.of(order);
    }

    @Transactional
    public void removeOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    public OrderResponse findOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException());
        return OrderResponse.of(order);
    }

    public TicketResponses findTicketsOfPerformance(Long performanceId, Pageable pageable) {
        Page<Ticket> tickets = ticketRepository.findByPerformanceId(performanceId, pageable);
        return TicketResponses.of(tickets);
    }
}
