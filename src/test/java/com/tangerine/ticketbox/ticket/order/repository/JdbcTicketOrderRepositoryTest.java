package com.tangerine.ticketbox.ticket.order.repository;

import com.tangerine.ticketbox.global.exception.SqlException;
import com.tangerine.ticketbox.ticket.order.TicketOrderTestData;
import com.tangerine.ticketbox.ticket.order.repository.model.TicketOrderEntity;
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
@Import(JdbcTicketOrderRepository.class)
class JdbcTicketOrderRepositoryTest {

    @Autowired
    JdbcTicketOrderRepository repository;

    @ParameterizedTest
    @DisplayName("존재하지 않는 주문 추가 시 성공한다.")
    @MethodSource("com.tangerine.ticketbox.ticket.order.TicketOrderTestData#provideEntities")
    void insert_NotExistOrderEntity_InsertOrderEntity(TicketOrderEntity order) {

        repository.insert(order);

        TicketOrderEntity result = repository.findById(order.orderId());
        assertThat(result).isEqualTo(order);
    }

    @ParameterizedTest
    @DisplayName("존재하는 주문 추가 시 실패한다.")
    @MethodSource("com.tangerine.ticketbox.ticket.order.TicketOrderTestData#provideEntities")
    void insert_ExistOrderEntity_Exception(TicketOrderEntity order) {
        repository.insert(order);

        Exception exception = catchException(() -> repository.insert(order));

        assertThat(exception).isInstanceOf(Exception.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 주문 업데이트 시 성공한다.")
    @MethodSource("com.tangerine.ticketbox.ticket.order.TicketOrderTestData#provideEntities")
    void update_ExistOrderEntity_UpdateOrderEntity(TicketOrderEntity order) {
        repository.insert(order);

        repository.update(TicketOrderTestData.newEntities(order));

        TicketOrderEntity result = repository.findById(order.orderId());
        assertThat(result).isEqualTo(TicketOrderTestData.newEntities(order));
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 주문 업데이트 실패한다.")
    @MethodSource("com.tangerine.ticketbox.ticket.order.TicketOrderTestData#provideEntities")
    void update_NotExistOrderEntity_Exception(TicketOrderEntity order) {

        Exception exception = catchException(() -> repository.update(order));

        assertThat(exception).isInstanceOf(SqlException.class);
    }

    @Test
    @DisplayName("모든 주문을 삭제 시 성공한다.")
    void deleteAll_Void_DeleteAllOrderEntity() {
        repository.deleteAll();

        List<TicketOrderEntity> result = repository.findAll();

        assertThat(result).isEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 주문을 아이디로 삭제 시 성공한다.")
    @MethodSource("com.tangerine.ticketbox.ticket.order.TicketOrderTestData#provideEntities")
    void deleteById_ExistOrderEntityId_DeleteOrderEntity(TicketOrderEntity order) {
        repository.insert(order);

        repository.deleteById(order.orderId());

        Exception exception = catchException(() -> repository.findById(order.orderId()));
        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 주문을 아이디로 삭제 시 실패한다.")
    @MethodSource("com.tangerine.ticketbox.ticket.order.TicketOrderTestData#provideEntities")
    void deleteById_NotExistOrderEntityId_Exception(TicketOrderEntity order) {

        repository.deleteById(order.orderId());

        Exception exception = catchException(() -> repository.findById(order.orderId()));
        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @ParameterizedTest
    @DisplayName("모든 주문을 조회한다.")
    @MethodSource("com.tangerine.ticketbox.ticket.order.TicketOrderTestData#provideEntities")
    void findAll_Void_ReturnOrderEntityList(TicketOrderEntity order) {
        System.out.println(order);
        repository.insert(order);

        List<TicketOrderEntity> result = repository.findAll();

        assertThat(result).isNotEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 주문을 아이디로 조회 시 성공한다.")
    @MethodSource("com.tangerine.ticketbox.ticket.order.TicketOrderTestData#provideEntities")
    void findById_ExistOrderEntity_ReturnOrderEntity(TicketOrderEntity order) {
        repository.insert(order);

        TicketOrderEntity result = repository.findById(order.orderId());

        assertThat(result).isEqualTo(order);
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 주문을 아이디로 조회 시 실패한다.")
    @MethodSource("com.tangerine.ticketbox.ticket.order.TicketOrderTestData#provideEntities")
    void findById_NotExistOrderEntity_ReturnNull(TicketOrderEntity order) {

        Exception exception = catchException(() -> repository.findById(order.orderId()));

        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 주문을 이메일로 조회 시 성공한다.")
    @MethodSource("com.tangerine.ticketbox.ticket.order.TicketOrderTestData#provideEntities")
    void findByEmail_ExistOrderEntity_ReturnOrderEntity(TicketOrderEntity order) {
        repository.insert(order);

        TicketOrderEntity result = repository.findByEmail(order.email());

        assertThat(result).isEqualTo(order);
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 주문을 아이디로 조회 시 실패한다.")
    @MethodSource("com.tangerine.ticketbox.ticket.order.TicketOrderTestData#provideEntities")
    void findByEmail_NotExistOrderEntity_ReturnNull(TicketOrderEntity order) {

        Exception exception = catchException(() -> repository.findByEmail(order.email()));

        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

}