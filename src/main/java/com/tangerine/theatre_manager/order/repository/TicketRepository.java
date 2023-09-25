package com.tangerine.theatre_manager.order.repository;

import com.tangerine.theatre_manager.order.model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Page<Ticket> findByPerformanceId(Long performanceId, Pageable pageable);

}
