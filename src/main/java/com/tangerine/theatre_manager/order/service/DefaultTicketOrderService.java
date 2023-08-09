package com.tangerine.theatre_manager.order.service;

import com.tangerine.theatre_manager.order.repository.TicketOrderRepository;
import com.tangerine.theatre_manager.order.repository.model.TicketOrderEntity;
import com.tangerine.theatre_manager.order.service.mapper.TicketOrderServiceMapper;
import com.tangerine.theatre_manager.order.service.model.TicketOrder;
import com.tangerine.theatre_manager.order.ticket.service.TicketService;
import com.tangerine.theatre_manager.order.vo.Email;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Profile({"test", "default"})
public class DefaultTicketOrderService implements TicketOrderService {

    private final TicketOrderRepository repository;
    private final TicketService ticketService;
    private final TicketOrderServiceMapper mapper;

    public DefaultTicketOrderService(TicketOrderRepository repository, TicketService ticketService, TicketOrderServiceMapper mapper) {
        this.repository = repository;
        this.ticketService = ticketService;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public void createOrder(TicketOrder ticketOrder) {
        repository.insert(mapper.domainToEntity(ticketOrder));
        ticketService.createTickets(ticketOrder.tickets());
    }

    @Override
    @Transactional
    public void deleteOrderById(UUID orderId) {
        ticketService.deleteTicketsByOrderId(orderId);
        repository.deleteById(orderId);
    }

    @Override
    @Transactional
    public TicketOrder findOrderById(UUID orderId) {
        TicketOrderEntity entity = repository.findById(orderId);
        return new TicketOrder(
                entity.orderId(),
                entity.email(),
                entity.orderedAt(),
                entity.ticketOrderStatus(),
                ticketService.findTicketsByOrderId(orderId));
    }

    @Override
    @Transactional
    public TicketOrder findOrderByEmail(Email email) {
        TicketOrderEntity entity = repository.findByEmail(email);
        return new TicketOrder(
                entity.orderId(),
                entity.email(),
                entity.orderedAt(),
                entity.ticketOrderStatus(),
                ticketService.findTicketsByOrderId(entity.orderId()));
    }

}
