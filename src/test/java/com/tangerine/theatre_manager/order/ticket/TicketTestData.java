package com.tangerine.theatre_manager.order.ticket;

import com.tangerine.theatre_manager.order.repository.model.TicketOrderEntity;
import com.tangerine.theatre_manager.order.ticket.model.Ticket;
import com.tangerine.theatre_manager.order.vo.Email;
import com.tangerine.theatre_manager.order.vo.TicketOrderStatus;
import com.tangerine.theatre_manager.performance.vo.Price;
import org.junit.jupiter.params.provider.Arguments;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class TicketTestData {

    public static final List<TicketOrderEntity> TICKET_ORDER_ENTITIES = List.of(
            new TicketOrderEntity(UUID.randomUUID(), new Email("apple@naver.com"), LocalDate.now(), TicketOrderStatus.ACCEPTED),
            new TicketOrderEntity(UUID.randomUUID(), new Email("strawberry@naver.com"), LocalDate.now(), TicketOrderStatus.RESERVED),
            new TicketOrderEntity(UUID.randomUUID(), new Email("grape@naver.com"), LocalDate.now(), TicketOrderStatus.PAYMENT_CONFIRMED),
            new TicketOrderEntity(UUID.randomUUID(), new Email("peach@naver.com"), LocalDate.now(), TicketOrderStatus.ACCEPTED)
    );

    public static final List<Ticket> TICKET_DOMAINS = List.of(
            new Ticket(UUID.randomUUID(), TICKET_ORDER_ENTITIES.get(0).orderId(), new Price(1000), LocalDate.now()),
            new Ticket(UUID.randomUUID(), TICKET_ORDER_ENTITIES.get(1).orderId(), new Price(28000), LocalDate.now()),
            new Ticket(UUID.randomUUID(), TICKET_ORDER_ENTITIES.get(2).orderId(), new Price(3000), LocalDate.now()),
            new Ticket(UUID.randomUUID(), TICKET_ORDER_ENTITIES.get(3).orderId(), new Price(55000), LocalDate.now())
    );

    static Stream<Arguments> provideDomains() {
        return TICKET_DOMAINS.stream()
                .map(Arguments::of);
    }
}
