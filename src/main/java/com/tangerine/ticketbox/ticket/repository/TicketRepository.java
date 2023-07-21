package com.tangerine.ticketbox.ticket.repository;

import java.util.List;
import java.util.UUID;

public interface TicketRepository {

    void insert(Ticket ticket);

    void update(Ticket ticket);

    void deleteAll();

    void deleteById(UUID ticketId);

    List<Ticket> findAll();

    Ticket findById(UUID ticketId);

    Ticket findByTheatreId(UUID theatreId);

}
