package com.tangerine.theatre_manager.order.ticket.repository;

import com.tangerine.theatre_manager.order.repository.JdbcTicketOrderRepository;
import com.tangerine.theatre_manager.order.ticket.TicketTestData;
import com.tangerine.theatre_manager.order.ticket.model.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

@JdbcTest
@ActiveProfiles("test")
@Import({JdbcTicketRepository.class, JdbcTicketOrderRepository.class})
class JdbcTicketRepositoryTest {

    @Autowired
    JdbcTicketRepository repository;

    @Autowired
    JdbcTicketOrderRepository orderRepository;

    @BeforeEach
    void setup() {
        TicketTestData.TICKET_ORDER_ENTITIES.forEach(orderRepository::insert);
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 티켓 추가 시 성공한다.")
    @MethodSource("com.tangerine.theatre_manager.order.ticket.TicketTestData#provideDomains")
    void insert_NotExistTicket_InsertTicket(Ticket ticket) {

        repository.insert(ticket);

        List<Ticket> result = repository.findByOrderId(ticket.orderId());
        assertThat(result).isNotEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 티켓 추가 시 실패한다.")
    @MethodSource("com.tangerine.theatre_manager.order.ticket.TicketTestData#provideDomains")
    void insert_ExistTicket_Exception(Ticket ticket) {
        repository.insert(ticket);

        Exception exception = catchException(() -> repository.insert(ticket));

        assertThat(exception).isInstanceOf(Exception.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 티켓을 주문 아이디로 삭제 시 성공한다.")
    @MethodSource("com.tangerine.theatre_manager.order.ticket.TicketTestData#provideDomains")
    void deleteByOrderId_ExistTicketId_DeleteTicket(Ticket ticket) {
        repository.insert(ticket);

        repository.deleteByOrderId(ticket.orderId());

        List<Ticket> result = repository.findByOrderId(ticket.orderId());
        assertThat(result).isEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 티켓을 주문 아이디로 삭제 시 실패한다.")
    @MethodSource("com.tangerine.theatre_manager.order.ticket.TicketTestData#provideDomains")
    void deleteByOrderId_NotExistTicketId_Exception(Ticket ticket) {

        repository.deleteByOrderId(ticket.orderId());

        List<Ticket> result = repository.findByOrderId(ticket.orderId());
        assertThat(result).isEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 티켓을 주문 아이디로 조회 시 성공한다.")
    @MethodSource("com.tangerine.theatre_manager.order.ticket.TicketTestData#provideDomains")
    void findByOrderId_ExistTicket_ReturnTicket(Ticket ticket) {
        repository.insert(ticket);

        List<Ticket> result = repository.findByOrderId(ticket.orderId());

        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("존재하지 않는 티켓을 주문 아이디로 조회 시 실패한다.")
    void findByOrderId_NotExistTicket_ReturnNull() {

        List<Ticket> result = repository.findByOrderId(TicketTestData.TICKET_ORDER_ENTITIES.get(0).orderId());

        assertThat(result).isEmpty();
    }

}