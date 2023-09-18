package com.tangerine.theatre_manager.order.repository;

import com.tangerine.theatre_manager.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
