package com.tangerine.ticketbox.ticket.order.repository;

import com.tangerine.ticketbox.ticket.order.repository.model.TicketOrderEntity;
import com.tangerine.ticketbox.ticket.order.vo.Email;

import java.util.List;
import java.util.UUID;

public interface TicketOrderRepository {

    void insert(TicketOrderEntity ticketOrderEntity);

    void update(TicketOrderEntity ticketOrderEntity);

    void deleteAll();

    void deleteById(UUID orderId);

    List<TicketOrderEntity> findAll();

    TicketOrderEntity findById(UUID orderId);

    TicketOrderEntity findByEmail(Email email);

}
