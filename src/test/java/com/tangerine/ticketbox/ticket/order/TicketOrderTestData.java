package com.tangerine.ticketbox.ticket.order;

import com.tangerine.ticketbox.theatre.repository.model.Theatre;
import com.tangerine.ticketbox.theatre.vo.AgeRate;
import com.tangerine.ticketbox.theatre.vo.Genre;
import com.tangerine.ticketbox.theatre.vo.Stage;
import com.tangerine.ticketbox.theatre.vo.TheatreName;
import com.tangerine.ticketbox.ticket.order.repository.model.TicketOrderEntity;
import com.tangerine.ticketbox.ticket.order.service.dto.TicketOrderParam;
import com.tangerine.ticketbox.ticket.order.service.mapper.TicketOrderServiceMapper;
import com.tangerine.ticketbox.ticket.order.service.model.TicketOrder;
import com.tangerine.ticketbox.ticket.order.vo.Email;
import com.tangerine.ticketbox.ticket.order.vo.TicketOrderStatus;
import com.tangerine.ticketbox.ticket.repository.dto.TicketEntity;
import com.tangerine.ticketbox.ticket.service.mapper.TicketServiceMapper;
import com.tangerine.ticketbox.ticket.service.model.Ticket;
import com.tangerine.ticketbox.ticket.vo.Price;
import com.tangerine.ticketbox.ticket.vo.Quantity;
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

    public static final List<Theatre> theatreDomains = List.of(
            new Theatre(UUID.randomUUID(), new TheatreName("밤의 여왕 아리아"), Genre.OPERA, AgeRate.ALL, LocalDate.of(2023, 1, 23), LocalDate.of(2023, 8, 14), Stage.A1),
            new Theatre(UUID.randomUUID(), new TheatreName("쉬어매드니스"), Genre.PLAY, AgeRate.FIFTEEN, LocalDate.of(2022, 6, 1), LocalDate.of(2023, 10, 31), Stage.A2),
            new Theatre(UUID.randomUUID(), new TheatreName("시라노"), Genre.MUSICAL, AgeRate.ADULT_ONLY, LocalDate.of(2022, 1, 1), LocalDate.of(2023, 1, 1), Stage.B1),
            new Theatre(UUID.randomUUID(), new TheatreName("라면"), Genre.PLAY, AgeRate.FIFTEEN, LocalDate.of(2023, 10, 23), LocalDate.of(2024, 3, 20), Stage.B2)
    );

    private static final List<TicketEntity> ticketEntities = List.of(
            new TicketEntity(UUID.randomUUID(), orderIds.get(0), theatreDomains.get(0).theatreId(), new Price(1000), new Quantity(2), LocalDate.now()),
            new TicketEntity(UUID.randomUUID(), orderIds.get(1), theatreDomains.get(1).theatreId(), new Price(28000), new Quantity(1), LocalDate.now()),
            new TicketEntity(UUID.randomUUID(), orderIds.get(2), theatreDomains.get(2).theatreId(), new Price(3000), new Quantity(22), LocalDate.now()),
            new TicketEntity(UUID.randomUUID(), orderIds.get(3), theatreDomains.get(3).theatreId(), new Price(55000), new Quantity(8), LocalDate.now())
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
        return new TicketOrderParam(param.orderId(), new Email("new-order@naver.com"), LocalDate.now(), TicketOrderStatus.ACCEPTED, param.tickets());
    }

    public static TicketOrder newDomain(TicketOrder domain) {
        return new TicketOrder(domain.getOrderId(), new Email("new-order@naver.com"), LocalDate.now(), TicketOrderStatus.ACCEPTED, domain.getTickets());
    }

    public static TicketOrderEntity newEntities(TicketOrderEntity entity) {
        return new TicketOrderEntity(entity.orderId(), new Email("new-order@naver.com"), LocalDate.now(), TicketOrderStatus.ACCEPTED);
    }

}
