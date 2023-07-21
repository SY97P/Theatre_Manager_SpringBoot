package com.tangerine.ticketbox.ticket.repository;

import com.tangerine.ticketbox.ticket.repository.dto.TicketEntity;

import java.util.List;
import java.util.UUID;

public interface TicketRepository {

    void insert(TicketEntity ticket);

    void update(TicketEntity ticket);

    void deleteAll();

    void deleteById(UUID ticketId);

    void deleteByOrderId(UUID orderId);

    List<TicketEntity> findAll();

    TicketEntity findById(UUID ticketId);

    TicketEntity findByTheatreId(UUID theatreId);

    List<TicketEntity> findByOrderId(UUID orderId);
}
