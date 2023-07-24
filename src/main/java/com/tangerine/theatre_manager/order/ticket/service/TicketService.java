package com.tangerine.theatre_manager.order.ticket.service;

import com.tangerine.theatre_manager.order.ticket.model.Ticket;
import com.tangerine.theatre_manager.order.ticket.repository.TicketRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Profile({"default", "test"})
public class TicketService {

    private final TicketRepository repository;

    public TicketService(TicketRepository repository) {
        this.repository = repository;
    }

    public void createTickets(List<Ticket> tickets) {
        tickets.forEach(repository::insert);
    }

    public void deleteTicketsByOrderId(UUID orderId) {
        repository.deleteByOrderId(orderId);
    }

    public List<Ticket> findTicketsByOrderId(UUID orderId) {
        return repository.findByOrderId(orderId);
    }
}
