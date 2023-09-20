package com.tangerine.theatre_manager.order.repository;

import com.tangerine.theatre_manager.order.model.Order;
import com.tangerine.theatre_manager.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findByUser(User user, Pageable pageable);
}
