package com.tangerine.theatre_manager.order.model;

import com.tangerine.theatre_manager.global.price.Price;
import com.tangerine.theatre_manager.performance.model.Performance;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDate;
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

    @Temporal(value = TemporalType.DATE)
    @Column(name = "view_date", nullable = false)
    private LocalDate viewDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id", nullable = false)
    private Performance performance;

    public Ticket(Price price, LocalDate viewDate, Order order, Performance performance) {
        this.price = price;
        this.viewDate = viewDate;
        this.order = order;
        this.performance = performance;
    }

    public long getPriceValue() {
        return price.getValue();
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
