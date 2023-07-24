package com.tangerine.theatre_manager.order;

import com.tangerine.theatre_manager.order.repository.model.TicketOrderEntity;
import com.tangerine.theatre_manager.order.service.mapper.TicketOrderServiceMapper;
import com.tangerine.theatre_manager.order.service.model.TicketOrder;
import com.tangerine.theatre_manager.order.ticket.model.Ticket;
import com.tangerine.theatre_manager.order.vo.Email;
import com.tangerine.theatre_manager.order.vo.TicketOrderStatus;
import com.tangerine.theatre_manager.performance.vo.Price;
import org.junit.jupiter.params.provider.Arguments;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TicketOrderTestData {

    private static final List<UUID> orderIds = IntStream.rangeClosed(0, 4)
            .mapToObj(i -> UUID.randomUUID())
            .toList();

    private static final List<Ticket> TICKET_DOMAINS = List.of(
            new Ticket(UUID.randomUUID(), orderIds.get(0), new Price(1000), LocalDate.now()),
            new Ticket(UUID.randomUUID(), orderIds.get(1), new Price(28000), LocalDate.now()),
            new Ticket(UUID.randomUUID(), orderIds.get(2), new Price(3000), LocalDate.now()),
            new Ticket(UUID.randomUUID(), orderIds.get(3), new Price(55000), LocalDate.now())
    );

    public static final List<TicketOrder> TICKET_ORDER_DOMAINS = List.of(
            new TicketOrder(orderIds.get(0), new Email("apple@naver.com"), LocalDate.now(), TicketOrderStatus.ACCEPTED, List.of(TICKET_DOMAINS.get(0))),
            new TicketOrder(orderIds.get(1), new Email("strawberry@naver.com"), LocalDate.now(), TicketOrderStatus.RESERVED, List.of(TICKET_DOMAINS.get(1))),
            new TicketOrder(orderIds.get(2), new Email("grape@naver.com"), LocalDate.now(), TicketOrderStatus.PAYMENT_CONFIRMED, List.of(TICKET_DOMAINS.get(2))),
            new TicketOrder(orderIds.get(3), new Email("peach@naver.com"), LocalDate.now(), TicketOrderStatus.ACCEPTED, List.of(TICKET_DOMAINS.get(3)))
    );

    public static final List<TicketOrderEntity> TICKET_ORDER_ENTITIES = TICKET_ORDER_DOMAINS.stream()
            .map(TicketOrderServiceMapper.INSTANCE::domainToEntity)
            .toList();

    public static Stream<Arguments> provideDomains() {
        return TICKET_ORDER_DOMAINS.stream()
                .map(Arguments::of);
    }

    public static Stream<Arguments> provideEntities() {
        return TICKET_ORDER_ENTITIES.stream()
                .map(Arguments::of);
    }

}
