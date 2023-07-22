package com.tangerine.theatre_manager.ticket.repository;

import com.tangerine.theatre_manager.global.exception.SqlException;
import com.tangerine.theatre_manager.performance.PerformanceTestData;
import com.tangerine.theatre_manager.performance.repository.JdbcPerformanceRepository;
import com.tangerine.theatre_manager.ticket.TicketTestData;
import com.tangerine.theatre_manager.ticket.order.repository.JdbcTicketOrderRepository;
import com.tangerine.theatre_manager.ticket.repository.dto.TicketEntity;
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
@Import({JdbcTicketRepository.class, JdbcPerformanceRepository.class, JdbcTicketOrderRepository.class})
class JdbcTicketRepositoryTest {

    @Autowired
    JdbcTicketRepository repository;

    @Autowired
    JdbcPerformanceRepository performanceRepository;
    @Autowired
    JdbcTicketOrderRepository orderRepository;

    @BeforeEach
    void setup() {
        TicketTestData.PERFORMANCE_DOMAINS.forEach(performanceRepository::insert);
        TicketTestData.orderEntities.forEach(orderRepository::insert);
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 티켓 추가 시 성공한다.")
    @MethodSource("com.tangerine.theatre_manager.ticket.TicketTestData#provideEntities")
    void insert_NotExistTicketEntity_InsertTicketEntity(TicketEntity ticket) {

        repository.insert(ticket);

        TicketEntity result = repository.findById(ticket.ticketId());
        assertThat(result).isEqualTo(ticket);
    }

    @ParameterizedTest
    @DisplayName("존재하는 티켓 추가 시 실패한다.")
    @MethodSource("com.tangerine.theatre_manager.ticket.TicketTestData#provideEntities")
    void insert_ExistTicketEntity_Exception(TicketEntity ticket) {
        repository.insert(ticket);

        Exception exception = catchException(() -> repository.insert(ticket));

        assertThat(exception).isInstanceOf(Exception.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 티켓 업데이트 시 성공한다.")
    @MethodSource("com.tangerine.theatre_manager.ticket.TicketTestData#provideEntities")
    void update_ExistTicketEntity_UpdateTicketEntity(TicketEntity ticket) {
        repository.insert(ticket);

        TicketEntity newTicketEntity = TicketTestData.newDomain(ticket);
        repository.update(newTicketEntity);

        TicketEntity result = repository.findById(ticket.ticketId());
        assertThat(result).isEqualTo(newTicketEntity);
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 티켓 업데이트 실패한다.")
    @MethodSource("com.tangerine.theatre_manager.ticket.TicketTestData#provideEntities")
    void update_NotExistTicketEntity_Exception(TicketEntity ticket) {

        Exception exception = catchException(() -> repository.update(ticket));

        assertThat(exception).isInstanceOf(SqlException.class);
    }

    @Test
    @DisplayName("모든 티켓을 삭제 시 성공한다.")
    void deleteAll_Void_DeleteAllTTicketEntity() {
        repository.deleteAll();

        List<TicketEntity> result = repository.findAll();

        assertThat(result).isEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 티켓을 아이디로 삭제 시 성공한다.")
    @MethodSource("com.tangerine.theatre_manager.ticket.TicketTestData#provideEntities")
    void deleteById_ExistTicketEntityId_DeleteTicketEntity(TicketEntity ticket) {
        repository.insert(ticket);

        repository.deleteById(ticket.ticketId());

        Exception exception = catchException(() -> repository.findById(ticket.ticketId()));
        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 티켓을 아이디로 삭제 시 실패한다.")
    @MethodSource("com.tangerine.theatre_manager.ticket.TicketTestData#provideEntities")
    void deleteById_NotExistTicketEntityId_Exception(TicketEntity ticket) {

        repository.deleteById(ticket.ticketId());

        Exception exception = catchException(() -> repository.findById(ticket.ticketId()));
        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 티켓을 주문 아이디로 삭제 시 성공한다.")
    @MethodSource("com.tangerine.theatre_manager.ticket.TicketTestData#provideEntities")
    void deleteByOrderId_ExistTicketEntityId_DeleteTicketEntity(TicketEntity ticket) {
        repository.insert(ticket);

        repository.deleteByOrderId(ticket.orderId());

        List<TicketEntity> result = repository.findByOrderId(ticket.orderId());
        assertThat(result).isEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 티켓을 주문 아이디로 삭제 시 실패한다.")
    @MethodSource("com.tangerine.theatre_manager.ticket.TicketTestData#provideEntities")
    void deleteByOrderId_NotExistTicketEntityId_Exception(TicketEntity ticket) {

        repository.deleteByOrderId(ticket.orderId());

        List<TicketEntity> result = repository.findByOrderId(ticket.orderId());
        assertThat(result).isEmpty();
    }

    @ParameterizedTest
    @DisplayName("모든 티켓을 조회한다.")
    @MethodSource("com.tangerine.theatre_manager.ticket.TicketTestData#provideEntities")
    void findAll_Void_ReturnTicketEntityList(TicketEntity ticket) {
        repository.insert(ticket);

        List<TicketEntity> result = repository.findAll();

        assertThat(result).isNotEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 티켓을 아이디로 조회 시 성공한다.")
    @MethodSource("com.tangerine.theatre_manager.ticket.TicketTestData#provideEntities")
    void findById_ExistTicketEntity_ReturnTicketEntity(TicketEntity ticket) {
        repository.insert(ticket);

        TicketEntity result = repository.findById(ticket.ticketId());

        assertThat(result).isEqualTo(ticket);
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 티켓을 아이디로 조회 시 실패한다.")
    @MethodSource("com.tangerine.theatre_manager.ticket.TicketTestData#provideEntities")
    void findById_NotExistTicketEntity_ReturnNull(TicketEntity ticket) {

        Exception exception = catchException(() -> repository.findById(ticket.ticketId()));

        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 티켓을 공연 아이디로 조회 시 성공한다.")
    @MethodSource("com.tangerine.theatre_manager.ticket.TicketTestData#provideEntities")
    void findByPerformanceId_ExistTicketEntity_ReturnTicketEntity(TicketEntity ticket) {
        repository.insert(ticket);

        TicketEntity result = repository.findByPerformanceId(ticket.performanceId());

        assertThat(result).isEqualTo(ticket);
    }

    @Test
    @DisplayName("존재하지 않는 티켓을 공연 아이디로 조회 시 실패한다.")
    void findByPerformanceId_NotExistTicketEntity_ReturnNull() {

        Exception exception = catchException(() -> repository.findById(PerformanceTestData.performanceDomains.get(0).performanceId()));

        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 티켓을 주문 아이디로 조회 시 성공한다.")
    @MethodSource("com.tangerine.theatre_manager.ticket.TicketTestData#provideEntities")
    void findByOrderId_ExistTicketEntity_ReturnTicketEntity(TicketEntity ticket) {
        repository.insert(ticket);

        List<TicketEntity> result = repository.findByOrderId(ticket.orderId());

        assertThat(result).isNotEmpty();
    }

    @Test
    @DisplayName("존재하지 않는 티켓을 주문 아이디로 조회 시 실패한다.")
    void findByOrderId_NotExistTicketEntity_ReturnNull() {

        List<TicketEntity> result = repository.findByOrderId(TicketTestData.PERFORMANCE_DOMAINS.get(0).performanceId());

        assertThat(result).isEmpty();
    }

}