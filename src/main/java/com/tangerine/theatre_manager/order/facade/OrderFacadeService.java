package com.tangerine.theatre_manager.order.facade;

import com.tangerine.theatre_manager.global.auth.JwtPrincipal;
import com.tangerine.theatre_manager.global.exception.PerformanceException;
import com.tangerine.theatre_manager.order.controller.OrderResponses;
import com.tangerine.theatre_manager.order.model.Order;
import com.tangerine.theatre_manager.order.model.Ticket;
import com.tangerine.theatre_manager.order.repository.OrderRepository;
import com.tangerine.theatre_manager.order.service.dto.OrderParam;
import com.tangerine.theatre_manager.order.service.dto.OrderResponse;
import com.tangerine.theatre_manager.order.service.dto.TicketParam;
import com.tangerine.theatre_manager.performance.model.Performance;
import com.tangerine.theatre_manager.performance.repository.PerformanceRepository;
import com.tangerine.theatre_manager.user.model.User;
import com.tangerine.theatre_manager.user.repository.UserRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderFacadeService {

    private final JwtPrincipal principal;
    private final OrderRepository orderRepository;
    private final PerformanceRepository performanceRepository;
    private final UserRepository userRepository;

    public OrderFacadeService(JwtPrincipal principal, OrderRepository orderRepository,
            PerformanceRepository performanceRepository,
            UserRepository userRepository) {
        this.principal = principal;
        this.orderRepository = orderRepository;
        this.performanceRepository = performanceRepository;
        this.userRepository = userRepository;
    }

    public OrderResponse registerOrder(OrderParam param) {
        Order order = OrderParam.to(getUser(), param);
        List<Ticket> tickets = param.tickets().stream()
                .map(ticketParam -> makeTicketEntity(ticketParam, order))
                .toList();
        order.addTickets(tickets);
        return OrderResponse.of(orderRepository.save(order));
    }

    public OrderResponses findUserOrders(Pageable pageable) {
        Page<Order> orders = orderRepository.findByUser(getUser(), pageable);
        return OrderResponses.of(orders);
    }

    private Ticket makeTicketEntity(TicketParam ticketParam, Order order) {
        Performance performance = performanceRepository.findById(ticketParam.performanceId())
                .orElseThrow(() -> new PerformanceException());
        return TicketParam.to(ticketParam, order, performance);
    }

    private User getUser() {
        return userRepository.findByEmail(principal.email())
                .orElseThrow(() -> new UsernameNotFoundException("해당 이메일의 유저가 없습니다."));
    }
}
