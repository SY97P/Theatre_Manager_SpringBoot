package com.tangerine.theatre_manager.ticket.repository;

import com.tangerine.theatre_manager.ticket.repository.dto.TicketEntity;

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

    TicketEntity findByPerformanceId(UUID performanceId);

    List<TicketEntity> findByOrderId(UUID orderId);
}
