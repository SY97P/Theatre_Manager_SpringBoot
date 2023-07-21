package com.tangerine.ticketbox.ticket.order.service;

import com.tangerine.ticketbox.global.exception.SqlException;
import com.tangerine.ticketbox.theatre.repository.JdbcTheatreRepository;
import com.tangerine.ticketbox.theatre.repository.TheatreRepository;
import com.tangerine.ticketbox.ticket.order.TicketOrderTestData;
import com.tangerine.ticketbox.ticket.order.repository.JdbcTicketOrderRepository;
import com.tangerine.ticketbox.ticket.order.service.dto.TicketOrderParam;
import com.tangerine.ticketbox.ticket.order.service.dto.TicketOrderResult;
import com.tangerine.ticketbox.ticket.repository.JdbcTicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

@JdbcTest
@ActiveProfiles("test")
@Import({DefaultTicketOrderService.class, JdbcTicketOrderRepository.class, JdbcTicketRepository.class, JdbcTheatreRepository.class})
class DefaultTicketOrderServiceTest {

    @Autowired
    DefaultTicketOrderService service;

    @Autowired
    TheatreRepository theatreRepository;

    @BeforeEach
    void setup() {
        TicketOrderTestData.theatreDomains.forEach(theatreRepository::insert);
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 주문 추가 시 성공한다.")
    @MethodSource("com.tangerine.ticketbox.ticket.order.TicketOrderTestData#provideParams")
    void insert_NotExistTheatre_InsertTheatre(TicketOrderParam order) {

        service.createOrder(order);

        TicketOrderResult result = service.findOrderById(order.getOrderId());
        assertThat(result.orderId()).isEqualTo(order.getOrderId());
    }

    @ParameterizedTest
    @DisplayName("존재하는 주문 추가 시 실패한다.")
    @MethodSource("com.tangerine.ticketbox.ticket.order.TicketOrderTestData#provideParams")
    void insert_ExistTheatre_Exception(TicketOrderParam order) {
        service.createOrder(order);

        Exception exception = catchException(() -> service.createOrder(order));

        assertThat(exception).isInstanceOf(Exception.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 주문 업데이트 시 성공한다.")
    @MethodSource("com.tangerine.ticketbox.ticket.order.TicketOrderTestData#provideParams")
    void update_ExistTheatre_UpdateTheatre(TicketOrderParam order) {
        service.createOrder(order);

        TicketOrderParam newParam = TicketOrderTestData.newParams(order);
        service.updateOrder(newParam);

        TicketOrderResult result = service.findOrderById(order.getOrderId());
        assertThat(result.orderId()).isEqualTo(newParam.getOrderId());
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 주문 업데이트 실패한다.")
    @MethodSource("com.tangerine.ticketbox.ticket.order.TicketOrderTestData#provideParams")
    void update_NotExistTheatre_Exception(TicketOrderParam order) {

        Exception exception = catchException(() -> service.updateOrder(order));

        assertThat(exception).isInstanceOf(SqlException.class);
    }

    @Test
    @DisplayName("모든 주문을 삭제 시 성공한다.")
    void deleteAll_Void_DeleteAllTheatre() {
        service.deleteAllOrders();

        List<TicketOrderResult> result = service.findAllOrders();

        assertThat(result).isEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 주문을 아이디로 삭제 시 성공한다.")
    @MethodSource("com.tangerine.ticketbox.ticket.order.TicketOrderTestData#provideParams")
    void deleteById_ExistTheatreId_DeleteTheatre(TicketOrderParam order) {
        service.createOrder(order);

        service.deleteOrderById(order.getOrderId());

        Exception exception = catchException(() -> service.findOrderById(order.getOrderId()));
        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 주문을 아이디로 삭제 시 실패한다.")
    @MethodSource("com.tangerine.ticketbox.ticket.order.TicketOrderTestData#provideParams")
    void deleteById_NotExistTheatreId_Exception(TicketOrderParam order) {

        service.deleteOrderById(order.getOrderId());

        Exception exception = catchException(() -> service.findOrderById(order.getOrderId()));
        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @ParameterizedTest
    @DisplayName("모든 주문을 조회한다.")
    @MethodSource("com.tangerine.ticketbox.ticket.order.TicketOrderTestData#provideParams")
    void findAll_Void_ReturnTheatreList(TicketOrderParam order) {
        service.createOrder(order);

        List<TicketOrderResult> result = service.findAllOrders();

        assertThat(result).isNotEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 주문을 아이디로 조회 시 성공한다.")
    @MethodSource("com.tangerine.ticketbox.ticket.order.TicketOrderTestData#provideParams")
    void findById_ExistTheatre_ReturnTheatre(TicketOrderParam order) {
        service.createOrder(order);

        TicketOrderResult result = service.findOrderById(order.getOrderId());

        assertThat(result.orderId()).isEqualTo(order.getOrderId());
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 주문을 아이디로 조회 시 실패한다.")
    @MethodSource("com.tangerine.ticketbox.ticket.order.TicketOrderTestData#provideParams")
    void findById_NotExistTheatre_ReturnNull(TicketOrderParam order) {

        Exception exception = catchException(() -> service.findOrderById(order.getOrderId()));

        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 주문을 이메일로 조회 시 성공한다.")
    @MethodSource("com.tangerine.ticketbox.ticket.order.TicketOrderTestData#provideParams")
    void findByEmail_ExistTheatre_ReturnTheatre(TicketOrderParam order) {
        service.createOrder(order);

        TicketOrderResult result = service.findOrderByEmail(order.getEmail());

        assertThat(result.orderId()).isEqualTo(order.getOrderId());
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 주문을 이메일로 조회 시 실패한다.")
    @MethodSource("com.tangerine.ticketbox.ticket.order.TicketOrderTestData#provideParams")
    void findByEmail_NotExistTheatre_ReturnNull(TicketOrderParam order) {

        Exception exception = catchException(() -> service.findOrderByEmail(order.getEmail()));

        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

}