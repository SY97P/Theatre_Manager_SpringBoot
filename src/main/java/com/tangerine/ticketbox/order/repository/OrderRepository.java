package com.tangerine.ticketbox.order.repository;

import com.tangerine.ticketbox.order.model.Email;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface OrderRepository {

    void insert(Order order);

    void update(Order order);

    void deleteAll();

    void deleteById(UUID orderId);

    List<Order> findAll();

    Order findById(UUID orderId);

    Order findByEmail(Email email);

}
