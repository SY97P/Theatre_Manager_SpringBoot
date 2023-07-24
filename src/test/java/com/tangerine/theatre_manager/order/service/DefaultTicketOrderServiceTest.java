package com.tangerine.theatre_manager.order.service;

import com.tangerine.theatre_manager.order.repository.JdbcTicketOrderRepository;
import com.tangerine.theatre_manager.order.service.model.TicketOrder;
import com.tangerine.theatre_manager.order.ticket.repository.JdbcTicketRepository;
import com.tangerine.theatre_manager.order.ticket.service.TicketService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

@JdbcTest
@ActiveProfiles("test")
@Import({DefaultTicketOrderService.class, JdbcTicketOrderRepository.class, TicketService.class, JdbcTicketRepository.class})
class DefaultTicketOrderServiceTest {

    @Autowired
    DefaultTicketOrderService service;

    @ParameterizedTest
    @DisplayName("존재하지 않는 주문 추가 시 성공한다.")
    @MethodSource("com.tangerine.theatre_manager.order.TicketOrderTestData#provideDomains")
    void insert_NotExistPerformance_InsertPerformance(TicketOrder order) {

        service.createOrder(order);

        TicketOrder result = service.findOrderById(order.orderId());
        assertThat(result.orderId()).isEqualTo(order.orderId());
    }

    @ParameterizedTest
    @DisplayName("존재하는 주문 추가 시 실패한다.")
    @MethodSource("com.tangerine.theatre_manager.order.TicketOrderTestData#provideDomains")
    void insert_ExistPerformance_Exception(TicketOrder order) {
        service.createOrder(order);

        Exception exception = catchException(() -> service.createOrder(order));

        assertThat(exception).isInstanceOf(Exception.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 주문을 아이디로 삭제 시 성공한다.")
    @MethodSource("com.tangerine.theatre_manager.order.TicketOrderTestData#provideDomains")
    void deleteById_ExistPerformanceId_DeletePerformance(TicketOrder order) {
        service.createOrder(order);

        service.deleteOrderById(order.orderId());

        Exception exception = catchException(() -> service.findOrderById(order.orderId()));
        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 주문을 아이디로 삭제 시 실패한다.")
    @MethodSource("com.tangerine.theatre_manager.order.TicketOrderTestData#provideDomains")
    void deleteById_NotExistPerformanceId_Exception(TicketOrder order) {

        service.deleteOrderById(order.orderId());

        Exception exception = catchException(() -> service.findOrderById(order.orderId()));
        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 주문을 아이디로 조회 시 성공한다.")
    @MethodSource("com.tangerine.theatre_manager.order.TicketOrderTestData#provideDomains")
    void findById_ExistPerformance_ReturnPerformance(TicketOrder order) {
        service.createOrder(order);

        TicketOrder result = service.findOrderById(order.orderId());

        assertThat(result.orderId()).isEqualTo(order.orderId());
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 주문을 아이디로 조회 시 실패한다.")
    @MethodSource("com.tangerine.theatre_manager.order.TicketOrderTestData#provideDomains")
    void findById_NotExistPerformance_ReturnNull(TicketOrder order) {

        Exception exception = catchException(() -> service.findOrderById(order.orderId()));

        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 주문을 이메일로 조회 시 성공한다.")
    @MethodSource("com.tangerine.theatre_manager.order.TicketOrderTestData#provideDomains")
    void findByEmail_ExistPerformance_ReturnPerformance(TicketOrder order) {
        service.createOrder(order);

        TicketOrder result = service.findOrderByEmail(order.email());

        assertThat(result.orderId()).isEqualTo(order.orderId());
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 주문을 이메일로 조회 시 실패한다.")
    @MethodSource("com.tangerine.theatre_manager.order.TicketOrderTestData#provideDomains")
    void findByEmail_NotExistPerformance_ReturnNull(TicketOrder order) {

        Exception exception = catchException(() -> service.findOrderByEmail(order.email()));

        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

}