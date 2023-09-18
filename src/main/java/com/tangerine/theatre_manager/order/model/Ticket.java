package com.tangerine.theatre_manager.order.model;

import com.tangerine.theatre_manager.global.price.Price;
import com.tangerine.theatre_manager.order.model.Order;
import com.tangerine.theatre_manager.performance.model.Performance;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Price price;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @OneToOne
    @JoinColumn(name = "performance_id", nullable = false)
    private Performance performance;

    public Ticket(Price price, Order order, Performance performance) {
        this.price = price;
        this.order = order;
        this.performance = performance;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
