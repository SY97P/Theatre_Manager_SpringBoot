package com.tangerine.theatre_manager.order.ticket.repository;

import com.tangerine.theatre_manager.order.ticket.model.Ticket;

import java.util.List;
import java.util.UUID;

public interface TicketRepository {

    void insert(Ticket ticket);

    void deleteByOrderId(UUID orderId);

    List<Ticket> findByOrderId(UUID orderId);

}
