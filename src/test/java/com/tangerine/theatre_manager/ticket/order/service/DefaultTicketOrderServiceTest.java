package com.tangerine.theatre_manager.ticket.order.service;

import com.tangerine.theatre_manager.global.exception.SqlException;
import com.tangerine.theatre_manager.performance.repository.JdbcPerformanceRepository;
import com.tangerine.theatre_manager.performance.repository.PerformanceRepository;
import com.tangerine.theatre_manager.ticket.order.TicketOrderTestData;
import com.tangerine.theatre_manager.ticket.order.repository.JdbcTicketOrderRepository;
import com.tangerine.theatre_manager.ticket.order.service.dto.TicketOrderParam;
import com.tangerine.theatre_manager.ticket.order.service.dto.TicketOrderResult;
import com.tangerine.theatre_manager.ticket.repository.JdbcTicketRepository;
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
@Import({DefaultTicketOrderService.class, JdbcTicketOrderRepository.class, JdbcTicketRepository.class, JdbcPerformanceRepository.class})
class DefaultTicketOrderServiceTest {

    @Autowired
    DefaultTicketOrderService service;

    @Autowired
    PerformanceRepository performanceRepository;

    @BeforeEach
    void setup() {
        TicketOrderTestData.PERFORMANCE_DOMAINS.forEach(performanceRepository::insert);
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 주문 추가 시 성공한다.")
    @MethodSource("com.tangerine.theatre_manager.ticket.order.TicketOrderTestData#provideParams")
    void insert_NotExistPerformance_InsertPerformance(TicketOrderParam order) {

        service.createOrder(order);

        TicketOrderResult result = service.findOrderById(order.getOrderId());
        assertThat(result.orderId()).isEqualTo(order.getOrderId());
    }

    @ParameterizedTest
    @DisplayName("존재하는 주문 추가 시 실패한다.")
    @MethodSource("com.tangerine.theatre_manager.ticket.order.TicketOrderTestData#provideParams")
    void insert_ExistPerformance_Exception(TicketOrderParam order) {
        service.createOrder(order);

        Exception exception = catchException(() -> service.createOrder(order));

        assertThat(exception).isInstanceOf(Exception.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 주문 업데이트 시 성공한다.")
    @MethodSource("com.tangerine.theatre_manager.ticket.order.TicketOrderTestData#provideParams")
    void update_ExistPerformance_UpdatePerformance(TicketOrderParam order) {
        service.createOrder(order);

        TicketOrderParam newParam = TicketOrderTestData.newParams(order);
        service.updateOrder(newParam);

        TicketOrderResult result = service.findOrderById(order.getOrderId());
        assertThat(result.orderId()).isEqualTo(newParam.getOrderId());
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 주문 업데이트 실패한다.")
    @MethodSource("com.tangerine.theatre_manager.ticket.order.TicketOrderTestData#provideParams")
    void update_NotExistPerformance_Exception(TicketOrderParam order) {

        Exception exception = catchException(() -> service.updateOrder(order));

        assertThat(exception).isInstanceOf(SqlException.class);
    }

    @Test
    @DisplayName("모든 주문을 삭제 시 성공한다.")
    void deleteAll_Void_DeleteAllPerformance() {
        service.deleteAllOrders();

        List<TicketOrderResult> result = service.findAllOrders();

        assertThat(result).isEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 주문을 아이디로 삭제 시 성공한다.")
    @MethodSource("com.tangerine.theatre_manager.ticket.order.TicketOrderTestData#provideParams")
    void deleteById_ExistPerformanceId_DeletePerformance(TicketOrderParam order) {
        service.createOrder(order);

        service.deleteOrderById(order.getOrderId());

        Exception exception = catchException(() -> service.findOrderById(order.getOrderId()));
        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 주문을 아이디로 삭제 시 실패한다.")
    @MethodSource("com.tangerine.theatre_manager.ticket.order.TicketOrderTestData#provideParams")
    void deleteById_NotExistPerformanceId_Exception(TicketOrderParam order) {

        service.deleteOrderById(order.getOrderId());

        Exception exception = catchException(() -> service.findOrderById(order.getOrderId()));
        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @ParameterizedTest
    @DisplayName("모든 주문을 조회한다.")
    @MethodSource("com.tangerine.theatre_manager.ticket.order.TicketOrderTestData#provideParams")
    void findAll_Void_ReturnPerformanceList(TicketOrderParam order) {
        service.createOrder(order);

        List<TicketOrderResult> result = service.findAllOrders();

        assertThat(result).isNotEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 주문을 아이디로 조회 시 성공한다.")
    @MethodSource("com.tangerine.theatre_manager.ticket.order.TicketOrderTestData#provideParams")
    void findById_ExistPerformance_ReturnPerformance(TicketOrderParam order) {
        service.createOrder(order);

        TicketOrderResult result = service.findOrderById(order.getOrderId());

        assertThat(result.orderId()).isEqualTo(order.getOrderId());
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 주문을 아이디로 조회 시 실패한다.")
    @MethodSource("com.tangerine.theatre_manager.ticket.order.TicketOrderTestData#provideParams")
    void findById_NotExistPerformance_ReturnNull(TicketOrderParam order) {

        Exception exception = catchException(() -> service.findOrderById(order.getOrderId()));

        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 주문을 이메일로 조회 시 성공한다.")
    @MethodSource("com.tangerine.theatre_manager.ticket.order.TicketOrderTestData#provideParams")
    void findByEmail_ExistPerformance_ReturnPerformance(TicketOrderParam order) {
        service.createOrder(order);

        TicketOrderResult result = service.findOrderByEmail(order.getEmail());

        assertThat(result.orderId()).isEqualTo(order.getOrderId());
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 주문을 이메일로 조회 시 실패한다.")
    @MethodSource("com.tangerine.theatre_manager.ticket.order.TicketOrderTestData#provideParams")
    void findByEmail_NotExistPerformance_ReturnNull(TicketOrderParam order) {

        Exception exception = catchException(() -> service.findOrderByEmail(order.getEmail()));

        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

}