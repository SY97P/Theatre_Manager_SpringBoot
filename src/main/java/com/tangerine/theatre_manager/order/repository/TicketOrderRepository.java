package com.tangerine.theatre_manager.order.repository;

import com.tangerine.theatre_manager.order.repository.model.TicketOrderEntity;
import com.tangerine.theatre_manager.order.vo.Email;

import java.util.List;
import java.util.UUID;

public interface TicketOrderRepository {

    void insert(TicketOrderEntity ticketOrderEntity);

    void deleteById(UUID orderId);

    List<TicketOrderEntity> findAll();

    TicketOrderEntity findById(UUID orderId);

    TicketOrderEntity findByEmail(Email email);

}
