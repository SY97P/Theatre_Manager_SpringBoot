package com.tangerine.theatre_manager.order.model;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;

import com.tangerine.theatre_manager.global.price.Price;
import com.tangerine.theatre_manager.order.model.vo.OrderStatus;
import com.tangerine.theatre_manager.user.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email", nullable = false)
    private User user;

    @Embedded
    private Price totalPrice;

    @Enumerated(value = EnumType.STRING)
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

    public Order(User user, Price totalPrice, OrderStatus orderStatus, LocalDate orderDate) {
        this.user = user;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.tickets = new ArrayList<>();
    }

    public String getEmail() {
        return user.getEmail();
    }

    public long getTotalPriceValue() {
        return totalPrice.getValue();
    }

    public String getOrderStatusName() {
        return orderStatus.name();
    }

    public void addTickets(List<Ticket> tickets) {
        tickets.forEach(ticket -> ticket.setOrder(this));
        this.totalPrice = new Price(sumAllTicketPrice(tickets));
        this.tickets = tickets;
    }

    public Order changeNextOrderStatus() {
        this.orderStatus = orderStatus.getNextStatus();
        return this;
    }

    private Long sumAllTicketPrice(List<Ticket> tickets) {
        return tickets.stream().map(Ticket::getPriceValue).reduce(0L, Long::sum);
    }

}
