package com.tangerine.theatre_manager.ticket.order;

import com.tangerine.theatre_manager.performance.repository.model.Performance;
import com.tangerine.theatre_manager.performance.vo.*;
import com.tangerine.theatre_manager.ticket.order.repository.model.TicketOrderEntity;
import com.tangerine.theatre_manager.ticket.order.service.dto.TicketOrderParam;
import com.tangerine.theatre_manager.ticket.order.service.mapper.TicketOrderServiceMapper;
import com.tangerine.theatre_manager.ticket.order.service.model.TicketOrder;
import com.tangerine.theatre_manager.ticket.order.vo.Email;
import com.tangerine.theatre_manager.ticket.order.vo.TicketOrderStatus;
import com.tangerine.theatre_manager.ticket.repository.dto.TicketEntity;
import com.tangerine.theatre_manager.ticket.service.mapper.TicketServiceMapper;
import com.tangerine.theatre_manager.ticket.service.model.Ticket;
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

    public static final List<Performance> PERFORMANCE_DOMAINS = List.of(
            new Performance(UUID.randomUUID(), new PerformanceName("밤의 여왕 아리아"), Genre.OPERA, AgeRate.ALL, LocalDate.of(2023, 1, 23), LocalDate.of(2023, 8, 14), Stage.A1, new Price(1000)),
            new Performance(UUID.randomUUID(), new PerformanceName("쉬어매드니스"), Genre.PLAY, AgeRate.FIFTEEN, LocalDate.of(2022, 6, 1), LocalDate.of(2023, 10, 31), Stage.A2, new Price(28000)),
            new Performance(UUID.randomUUID(), new PerformanceName("시라노"), Genre.MUSICAL, AgeRate.ADULT_ONLY, LocalDate.of(2022, 1, 1), LocalDate.of(2023, 1, 1), Stage.B1, new Price(3000)),
            new Performance(UUID.randomUUID(), new PerformanceName("라면"), Genre.PLAY, AgeRate.FIFTEEN, LocalDate.of(2023, 10, 23), LocalDate.of(2024, 3, 20), Stage.B2, new Price(55000))
    );

    private static final List<TicketEntity> ticketEntities = List.of(
            new TicketEntity(UUID.randomUUID(), orderIds.get(0), PERFORMANCE_DOMAINS.get(0).performanceId(), new Price(1000), LocalDate.now()),
            new TicketEntity(UUID.randomUUID(), orderIds.get(1), PERFORMANCE_DOMAINS.get(1).performanceId(), new Price(28000), LocalDate.now()),
            new TicketEntity(UUID.randomUUID(), orderIds.get(2), PERFORMANCE_DOMAINS.get(2).performanceId(), new Price(3000), LocalDate.now()),
            new TicketEntity(UUID.randomUUID(), orderIds.get(3), PERFORMANCE_DOMAINS.get(3).performanceId(), new Price(55000), LocalDate.now())
    );

    private static final List<Ticket> ticketDomains = ticketEntities.stream()
            .map(TicketServiceMapper.INSTANCE::entityToDomain)
            .toList();

    public static List<TicketOrderParam> ticketOrderParams = List.of(
            new TicketOrderParam(orderIds.get(0), new Email("apple@naver.com"), LocalDate.now(), TicketOrderStatus.ACCEPTED, List.of(ticketDomains.get(0))),
            new TicketOrderParam(orderIds.get(1), new Email("strawberry@naver.com"), LocalDate.now(), TicketOrderStatus.RESERVED, List.of(ticketDomains.get(1))),
            new TicketOrderParam(orderIds.get(2), new Email("grape@naver.com"), LocalDate.now(), TicketOrderStatus.PAYMENT_CONFIRMED, List.of(ticketDomains.get(2))),
            new TicketOrderParam(orderIds.get(3), new Email("peach@naver.com"), LocalDate.now(), TicketOrderStatus.ACCEPTED, List.of(ticketDomains.get(3)))
    );

    public static List<TicketOrder> ticketOrderDomains = ticketOrderParams.stream()
            .map(TicketOrderServiceMapper.INSTANCE::paramToDomain)
            .toList();

    public static List<TicketOrderEntity> orderEntities = ticketOrderDomains.stream()
            .map(TicketOrderServiceMapper.INSTANCE::domainToEntity)
            .toList();

    public static Stream<Arguments> provideParams() {
        return ticketOrderParams.stream()
                .map(Arguments::of);
    }

    public static Stream<Arguments> provideDomains() {
        return ticketOrderDomains.stream()
                .map(Arguments::of);
    }

    public static Stream<Arguments> provideEntities() {
        return orderEntities.stream()
                .map(Arguments::of);
    }

    public static TicketOrderParam newParams(TicketOrderParam param) {
        return new TicketOrderParam(param.getOrderId(), new Email("new-order@naver.com"), LocalDate.now(), TicketOrderStatus.ACCEPTED, param.getTickets());
    }

    public static TicketOrder newDomain(TicketOrder domain) {
        return new TicketOrder(domain.getOrderId(), new Email("new-order@naver.com"), LocalDate.now(), TicketOrderStatus.ACCEPTED, domain.getTickets());
    }

    public static TicketOrderEntity newEntities(TicketOrderEntity entity) {
        return new TicketOrderEntity(entity.orderId(), new Email("new-order@naver.com"), LocalDate.now(), TicketOrderStatus.ACCEPTED);
    }

}
