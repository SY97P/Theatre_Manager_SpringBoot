package com.tangerine.ticketbox.order.service;

import com.tangerine.ticketbox.global.exception.SqlException;
import com.tangerine.ticketbox.order.OrderTestData;
import com.tangerine.ticketbox.order.repository.JdbcOrderRepository;
import com.tangerine.ticketbox.order.service.dto.OrderParam;
import com.tangerine.ticketbox.order.service.dto.OrderResult;
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
@Import({DefaultOrderService.class, JdbcOrderRepository.class})
class DefaultOrderServiceTest {

    @Autowired
    DefaultOrderService service;


    @ParameterizedTest
    @DisplayName("존재하지 않는 주문 추가 시 성공한다.")
    @MethodSource("com.tangerine.ticketbox.order.OrderTestData#provideParams")
    void insert_NotExistTheatre_InsertTheatre(OrderParam order) {

        service.createOrder(order);

        OrderResult result = service.findOrderById(order.orderId());
        assertThat(result.orderId()).isEqualTo(order.orderId());
    }

    @ParameterizedTest
    @DisplayName("존재하는 주문 추가 시 실패한다.")
    @MethodSource("com.tangerine.ticketbox.order.OrderTestData#provideParams")
    void insert_ExistTheatre_Exception(OrderParam order) {
        service.createOrder(order);

        Exception exception = catchException(() -> service.createOrder(order));

        assertThat(exception).isInstanceOf(Exception.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 주문 업데이트 시 성공한다.")
    @MethodSource("com.tangerine.ticketbox.order.OrderTestData#provideParams")
    void update_ExistTheatre_UpdateTheatre(OrderParam order) {
        service.createOrder(order);

        OrderParam newParam = OrderTestData.newParams(order);
        service.updateOrder(newParam);

        OrderResult result = service.findOrderById(order.orderId());
        assertThat(result.orderId()).isEqualTo(newParam.orderId());
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 주문 업데이트 실패한다.")
    @MethodSource("com.tangerine.ticketbox.order.OrderTestData#provideParams")
    void update_NotExistTheatre_Exception(OrderParam order) {

        Exception exception = catchException(() -> service.updateOrder(order));

        assertThat(exception).isInstanceOf(SqlException.class);
    }

    @Test
    @DisplayName("모든 주문을 삭제 시 성공한다.")
    void deleteAll_Void_DeleteAllTheatre() {
        service.deleteAllOrders();

        List<OrderResult> result = service.findAllOrders();

        assertThat(result).isEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 주문을 아이디로 삭제 시 성공한다.")
    @MethodSource("com.tangerine.ticketbox.order.OrderTestData#provideParams")
    void deleteById_ExistTheatreId_DeleteTheatre(OrderParam order) {
        service.createOrder(order);

        service.deleteOrderById(order.orderId());

        Exception exception = catchException(() -> service.findOrderById(order.orderId()));
        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 주문을 아이디로 삭제 시 실패한다.")
    @MethodSource("com.tangerine.ticketbox.order.OrderTestData#provideParams")
    void deleteById_NotExistTheatreId_Exception(OrderParam order) {

        service.deleteOrderById(order.orderId());

        Exception exception = catchException(() -> service.findOrderById(order.orderId()));
        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @ParameterizedTest
    @DisplayName("모든 주문을 조회한다.")
    @MethodSource("com.tangerine.ticketbox.order.OrderTestData#provideParams")
    void findAll_Void_ReturnTheatreList(OrderParam order) {
        service.createOrder(order);

        List<OrderResult> result = service.findAllOrders();

        assertThat(result).isNotEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 주문을 아이디로 조회 시 성공한다.")
    @MethodSource("com.tangerine.ticketbox.order.OrderTestData#provideParams")
    void findById_ExistTheatre_ReturnTheatre(OrderParam order) {
        service.createOrder(order);

        OrderResult result = service.findOrderById(order.orderId());

        assertThat(result.orderId()).isEqualTo(order.orderId());
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 주문을 아이디로 조회 시 실패한다.")
    @MethodSource("com.tangerine.ticketbox.order.OrderTestData#provideParams")
    void findById_NotExistTheatre_ReturnNull(OrderParam order) {

        Exception exception = catchException(() -> service.findOrderById(order.orderId()));

        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 주문을 이메일로 조회 시 성공한다.")
    @MethodSource("com.tangerine.ticketbox.order.OrderTestData#provideParams")
    void findByEmail_ExistTheatre_ReturnTheatre(OrderParam order) {
        service.createOrder(order);

        OrderResult result = service.findOrderByEmail(order.email());

        assertThat(result.orderId()).isEqualTo(order.orderId());
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 주문을 이메일로 조회 시 실패한다.")
    @MethodSource("com.tangerine.ticketbox.order.OrderTestData#provideParams")
    void findByEmail_NotExistTheatre_ReturnNull(OrderParam order) {

        Exception exception = catchException(() -> service.findOrderByEmail(order.email()));

        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

}