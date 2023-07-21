package com.tangerine.ticketbox.order.repository;

import com.tangerine.ticketbox.global.exception.SqlException;
import com.tangerine.ticketbox.order.OrderTestData;
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
@Import(JdbcOrderRepository.class)
class JdbcOrderRepositoryTest {

    @Autowired
    JdbcOrderRepository repository;
    
    @ParameterizedTest
    @DisplayName("존재하지 않는 주문 추가 시 성공한다.")
    @MethodSource("com.tangerine.ticketbox.order.OrderTestData#provideDomains")
    void insert_NotExistOrder_InsertOrder(Order order) {

        repository.insert(order);

        Order result = repository.findById(order.orderId());
        assertThat(result).isEqualTo(order);
    }

    @ParameterizedTest
    @DisplayName("존재하는 주문 추가 시 실패한다.")
    @MethodSource("com.tangerine.ticketbox.order.OrderTestData#provideDomains")
    void insert_ExistOrder_Exception(Order order) {
        repository.insert(order);

        Exception exception = catchException(() -> repository.insert(order));

        assertThat(exception).isInstanceOf(Exception.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 주문 업데이트 시 성공한다.")
    @MethodSource("com.tangerine.ticketbox.order.OrderTestData#provideDomains")
    void update_ExistOrder_UpdateOrder(Order order) {
        repository.insert(order);

        repository.update(OrderTestData.newDomain(order));

        Order result = repository.findById(order.orderId());
        assertThat(result).isEqualTo(OrderTestData.newDomain(order));
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 주문 업데이트 실패한다.")
    @MethodSource("com.tangerine.ticketbox.order.OrderTestData#provideDomains")
    void update_NotExistOrder_Exception(Order order) {

        Exception exception = catchException(() -> repository.update(order));

        assertThat(exception).isInstanceOf(SqlException.class);
    }

    @Test
    @DisplayName("모든 주문을 삭제 시 성공한다.")
    void deleteAll_Void_DeleteAllOrder() {
        repository.deleteAll();

        List<Order> result = repository.findAll();

        assertThat(result).isEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 주문을 아이디로 삭제 시 성공한다.")
    @MethodSource("com.tangerine.ticketbox.order.OrderTestData#provideDomains")
    void deleteById_ExistOrderId_DeleteOrder(Order order) {
        repository.insert(order);

        repository.deleteById(order.orderId());

        Exception exception = catchException(() -> repository.findById(order.orderId()));
        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 주문을 아이디로 삭제 시 실패한다.")
    @MethodSource("com.tangerine.ticketbox.order.OrderTestData#provideDomains")
    void deleteById_NotExistOrderId_Exception(Order order) {

        repository.deleteById(order.orderId());

        Exception exception = catchException(() -> repository.findById(order.orderId()));
        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @ParameterizedTest
    @DisplayName("모든 주문을 조회한다.")
    @MethodSource("com.tangerine.ticketbox.order.OrderTestData#provideDomains")
    void findAll_Void_ReturnOrderList(Order order) {
        repository.insert(order);

        List<Order> result = repository.findAll();

        assertThat(result).isNotEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 주문을 아이디로 조회 시 성공한다.")
    @MethodSource("com.tangerine.ticketbox.order.OrderTestData#provideDomains")
    void findById_ExistOrder_ReturnOrder(Order order) {
        repository.insert(order);

        Order result = repository.findById(order.orderId());

        assertThat(result).isEqualTo(order);
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 주문을 아이디로 조회 시 실패한다.")
    @MethodSource("com.tangerine.ticketbox.order.OrderTestData#provideDomains")
    void findById_NotExistOrder_ReturnNull(Order order) {

        Exception exception = catchException(() -> repository.findById(order.orderId()));

        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 주문을 이메일로 조회 시 성공한다.")
    @MethodSource("com.tangerine.ticketbox.order.OrderTestData#provideDomains")
    void findByEmail_ExistOrder_ReturnOrder(Order order) {
        repository.insert(order);

        Order result = repository.findByEmail(order.email());

        assertThat(result).isEqualTo(order);
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 주문을 아이디로 조회 시 실패한다.")
    @MethodSource("com.tangerine.ticketbox.order.OrderTestData#provideDomains")
    void findByEmail_NotExistOrder_ReturnNull(Order order) {

        Exception exception = catchException(() -> repository.findByEmail(order.email()));

        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

}