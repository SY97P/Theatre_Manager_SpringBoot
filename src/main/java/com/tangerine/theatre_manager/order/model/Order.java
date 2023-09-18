package com.tangerine.theatre_manager.order.model;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;

import com.tangerine.theatre_manager.global.price.Price;
import com.tangerine.theatre_manager.order.model.vo.Email;
import com.tangerine.theatre_manager.order.model.vo.OrderStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Email email;

    @Embedded
    private Price price;

    @Enumerated
    private OrderStatus orderStatus;

    @CreatedDate
    @Column(name = "ordered_date")
    private LocalDate orderDate;

    @OneToMany(
            mappedBy = "order",
            fetch = FetchType.LAZY,
            cascade = {PERSIST, REMOVE},
            orphanRemoval = true)
    private List<Ticket> tickets;

    public Order(Email email, Price price, OrderStatus orderStatus, LocalDate orderDate) {
        this.email = email;
        this.price = price;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
    }

    public Order(Email email, Price price, OrderStatus orderStatus, LocalDate orderDate, List<Ticket> tickets) {
        this.email = email;
        this.price = price;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.tickets = tickets;
    }

    public void addTicket(Ticket ticket) {
        ticket.setOrder(this);
        this.tickets.add(ticket);
    }
}