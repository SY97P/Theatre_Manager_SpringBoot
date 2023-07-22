package com.tangerine.theatre_manager.ticket;

import com.tangerine.theatre_manager.performance.repository.model.Performance;
import com.tangerine.theatre_manager.performance.vo.AgeRate;
import com.tangerine.theatre_manager.performance.vo.Genre;
import com.tangerine.theatre_manager.performance.vo.PerformanceName;
import com.tangerine.theatre_manager.performance.vo.Stage;
import com.tangerine.theatre_manager.ticket.order.repository.model.TicketOrderEntity;
import com.tangerine.theatre_manager.ticket.order.vo.Email;
import com.tangerine.theatre_manager.ticket.order.vo.TicketOrderStatus;
import com.tangerine.theatre_manager.ticket.repository.dto.TicketEntity;
import com.tangerine.theatre_manager.ticket.service.mapper.TicketServiceMapper;
import com.tangerine.theatre_manager.ticket.service.model.Ticket;
import com.tangerine.theatre_manager.ticket.vo.Price;
import com.tangerine.theatre_manager.ticket.vo.Quantity;
import org.junit.jupiter.params.provider.Arguments;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class TicketTestData {

    public static final List<Performance> PERFORMANCE_DOMAINS = List.of(
            new Performance(UUID.randomUUID(), new PerformanceName("밤의 여왕 아리아"), Genre.OPERA, AgeRate.ALL, LocalDate.of(2023, 1, 23), LocalDate.of(2023, 8, 14), Stage.A1),
            new Performance(UUID.randomUUID(), new PerformanceName("쉬어매드니스"), Genre.PLAY, AgeRate.FIFTEEN, LocalDate.of(2022, 6, 1), LocalDate.of(2023, 10, 31), Stage.A2),
            new Performance(UUID.randomUUID(), new PerformanceName("시라노"), Genre.MUSICAL, AgeRate.ADULT_ONLY, LocalDate.of(2022, 1, 1), LocalDate.of(2023, 1, 1), Stage.B1),
            new Performance(UUID.randomUUID(), new PerformanceName("라면"), Genre.PLAY, AgeRate.FIFTEEN, LocalDate.of(2023, 10, 23), LocalDate.of(2024, 3, 20), Stage.B2)
    );

    public static final List<TicketOrderEntity> orderEntities = List.of(
            new TicketOrderEntity(UUID.randomUUID(), new Email("apple@naver.com"), LocalDate.now(), TicketOrderStatus.ACCEPTED),
            new TicketOrderEntity(UUID.randomUUID(), new Email("strawberry@naver.com"), LocalDate.now(), TicketOrderStatus.RESERVED),
            new TicketOrderEntity(UUID.randomUUID(), new Email("grape@naver.com"), LocalDate.now(), TicketOrderStatus.PAYMENT_CONFIRMED),
            new TicketOrderEntity(UUID.randomUUID(), new Email("peach@naver.com"), LocalDate.now(), TicketOrderStatus.ACCEPTED)
    );

    public static List<TicketEntity> ticketEntities = List.of(
            new TicketEntity(UUID.randomUUID(), orderEntities.get(0).orderId(), PERFORMANCE_DOMAINS.get(0).performanceId(), new Price(1000), new Quantity(2), LocalDate.now()),
            new TicketEntity(UUID.randomUUID(), orderEntities.get(1).orderId(), PERFORMANCE_DOMAINS.get(1).performanceId(), new Price(28000), new Quantity(1), LocalDate.now()),
            new TicketEntity(UUID.randomUUID(), orderEntities.get(2).orderId(), PERFORMANCE_DOMAINS.get(2).performanceId(), new Price(3000), new Quantity(22), LocalDate.now()),
            new TicketEntity(UUID.randomUUID(), orderEntities.get(3).orderId(), PERFORMANCE_DOMAINS.get(3).performanceId(), new Price(55000), new Quantity(8), LocalDate.now())
    );

    public static TicketEntity newDomain(TicketEntity ticket) {
        return new TicketEntity(ticket.ticketId(), orderEntities.get(0).orderId(), ticket.performanceId(), new Price(1), new Quantity(1), LocalDate.now());
    }

    public static List<Ticket> ticketDomains = ticketEntities.stream()
            .map(TicketServiceMapper.INSTANCE::entityToDomain)
            .toList();

    static Stream<Arguments> provideEntities() {
        return ticketEntities.stream()
                .map(Arguments::of);
    }
}
