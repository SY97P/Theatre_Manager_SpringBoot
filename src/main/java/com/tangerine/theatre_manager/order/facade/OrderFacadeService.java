package com.tangerine.theatre_manager.order.facade;

import com.tangerine.theatre_manager.global.exception.PerformanceException;
import com.tangerine.theatre_manager.order.model.Order;
import com.tangerine.theatre_manager.order.model.Ticket;
import com.tangerine.theatre_manager.order.repository.OrderRepository;
import com.tangerine.theatre_manager.order.service.dto.OrderParam;
import com.tangerine.theatre_manager.order.service.dto.OrderResponse;
import com.tangerine.theatre_manager.order.service.dto.TicketParam;
import com.tangerine.theatre_manager.performance.model.Performance;
import com.tangerine.theatre_manager.performance.repository.PerformanceRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderFacadeService {

    private final OrderRepository orderRepository;
    private final PerformanceRepository performanceRepository;

    public OrderFacadeService(OrderRepository orderRepository, PerformanceRepository performanceRepository) {
        this.orderRepository = orderRepository;
        this.performanceRepository = performanceRepository;
    }

    public OrderResponse registerOrder(OrderParam param) {
        Order order = OrderParam.to(param);
        List<Ticket> tickets = param.tickets().stream()
                .map(ticketParam -> {
                    Performance performance = performanceRepository.findById(ticketParam.performanceId())
                            .orElseThrow(() -> new PerformanceException());
                    return TicketParam.to(order, performance);
                }).toList();
        order.addTickets(tickets);
        return OrderResponse.of(orderRepository.save(order));
    }

}
