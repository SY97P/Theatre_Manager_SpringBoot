package com.tangerine.ticketbox.ticket.order.service;

import com.tangerine.ticketbox.ticket.order.repository.TicketOrderRepository;
import com.tangerine.ticketbox.ticket.order.service.dto.TicketOrderParam;
import com.tangerine.ticketbox.ticket.order.service.dto.TicketOrderResult;
import com.tangerine.ticketbox.ticket.order.service.mapper.TicketOrderServiceMapper;
import com.tangerine.ticketbox.ticket.order.service.model.TicketOrder;
import com.tangerine.ticketbox.ticket.order.vo.Email;
import com.tangerine.ticketbox.ticket.repository.TicketRepository;
import com.tangerine.ticketbox.ticket.service.mapper.TicketServiceMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Profile({"test", "default"})
public class DefaultTicketOrderService implements TicketOrderService {

    private final TicketOrderRepository ticketOrderRepository;
    private final TicketRepository ticketRepository;

    public DefaultTicketOrderService(TicketOrderRepository ticketOrderRepository, TicketRepository ticketRepository) {
        this.ticketOrderRepository = ticketOrderRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    @Transactional
    public void createOrder(TicketOrderParam param) {
        TicketOrder ticketOrder = TicketOrderServiceMapper.INSTANCE.paramToDomain(param);
        ticketOrderRepository.insert(TicketOrderServiceMapper.INSTANCE.domainToEntity(ticketOrder));
        ticketOrder.getTickets().stream()
                .map(TicketServiceMapper.INSTANCE::domainToEntity)
                .forEach(ticketRepository::insert);
    }

    @Override
    @Transactional
    public void updateOrder(TicketOrderParam param) {
        TicketOrder ticketOrder = TicketOrderServiceMapper.INSTANCE.paramToDomain(param);
        ticketOrderRepository.update(TicketOrderServiceMapper.INSTANCE.domainToEntity(ticketOrder));
        ticketOrder.getTickets().stream()
                .map(TicketServiceMapper.INSTANCE::domainToEntity)
                .forEach(ticketRepository::update);
    }

    @Override
    @Transactional
    public void deleteAllOrders() {
        ticketRepository.deleteAll();
        ticketOrderRepository.deleteAll();
    }

    @Override
    @Transactional
    public void deleteOrderById(UUID orderId) {
        ticketRepository.deleteByOrderId(orderId);
        ticketOrderRepository.deleteById(orderId);
    }

    @Override
    @Transactional
    public List<TicketOrderResult> findAllOrders() {
        List<TicketOrder> ticketOrders = ticketOrderRepository.findAll()
                .stream()
                .map(TicketOrderServiceMapper.INSTANCE::entityToDomain)
                .toList();
        for (TicketOrder ticketOrder : ticketOrders) {
            ticketOrder.setTickets(
                    ticketRepository.findByOrderId(ticketOrder.getOrderId())
                            .stream()
                            .map(TicketServiceMapper.INSTANCE::entityToDomain)
                            .toList());
        }
        return ticketOrders.stream()
                .map(TicketOrderServiceMapper.INSTANCE::domainToResult)
                .toList();
    }

    @Override
    @Transactional
    public TicketOrderResult findOrderById(UUID orderId) {
        TicketOrder ticketOrder = TicketOrderServiceMapper.INSTANCE.entityToDomain(ticketOrderRepository.findById(orderId));
        ticketOrder.setTickets(
                ticketRepository.findByOrderId(ticketOrder.getOrderId())
                        .stream()
                        .map(TicketServiceMapper.INSTANCE::entityToDomain)
                        .toList());
        return TicketOrderServiceMapper.INSTANCE.domainToResult(ticketOrder);
    }

    @Override
    @Transactional
    public TicketOrderResult findOrderByEmail(Email email) {
        TicketOrder ticketOrder = TicketOrderServiceMapper.INSTANCE.entityToDomain(ticketOrderRepository.findByEmail(email));
        ticketOrder.setTickets(
                ticketRepository.findByOrderId(ticketOrder.getOrderId())
                        .stream()
                        .map(TicketServiceMapper.INSTANCE::entityToDomain)
                        .toList());
        return TicketOrderServiceMapper.INSTANCE.domainToResult(ticketOrder);
    }

}
