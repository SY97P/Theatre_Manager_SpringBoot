package com.tangerine.ticketbox.ticket.repository;

import com.tangerine.ticketbox.global.exception.SqlException;
import com.tangerine.ticketbox.theatre.repository.JdbcTheatreRepository;
import com.tangerine.ticketbox.ticket.TicketTestData;
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
@Import({JdbcTicketRepository.class, JdbcTheatreRepository.class})
class JdbcTicketRepositoryTest {

    @Autowired
    JdbcTicketRepository repository;

    @Autowired
    JdbcTheatreRepository theatreRepository;

    @BeforeEach
    void setup() {
        TicketTestData.theatreDomains.forEach(theatreRepository::insert);
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 티켓 추가 시 성공한다.")
    @MethodSource("com.tangerine.ticketbox.ticket.TicketTestData#provideDomains")
    void insert_NotExistTicket_InsertTicket(Ticket ticket) {

        repository.insert(ticket);

        Ticket result = repository.findById(ticket.ticketId());
        assertThat(result).isEqualTo(ticket);
    }

    @ParameterizedTest
    @DisplayName("존재하는 티켓 추가 시 실패한다.")
    @MethodSource("com.tangerine.ticketbox.ticket.TicketTestData#provideDomains")
    void insert_ExistTicket_Exception(Ticket ticket) {
        repository.insert(ticket);

        Exception exception = catchException(() -> repository.insert(ticket));

        assertThat(exception).isInstanceOf(Exception.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 티켓 업데이트 시 성공한다.")
    @MethodSource("com.tangerine.ticketbox.ticket.TicketTestData#provideDomains")
    void update_ExistTicket_UpdateTicket(Ticket ticket) {
        repository.insert(ticket);

        Ticket newTicket = TicketTestData.newDomain(ticket);
        repository.update(newTicket);

        Ticket result = repository.findById(ticket.ticketId());
        assertThat(result).isEqualTo(newTicket);
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 티켓 업데이트 실패한다.")
    @MethodSource("com.tangerine.ticketbox.ticket.TicketTestData#provideDomains")
    void update_NotExistTicket_Exception(Ticket ticket) {

        Exception exception = catchException(() -> repository.update(ticket));

        assertThat(exception).isInstanceOf(SqlException.class);
    }

    @Test
    @DisplayName("모든 티켓을 삭제 시 성공한다.")
    void deleteAll_Void_DeleteAllTTicket() {
        repository.deleteAll();

        List<Ticket> result = repository.findAll();

        assertThat(result).isEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 티켓을 아이디로 삭제 시 성공한다.")
    @MethodSource("com.tangerine.ticketbox.ticket.TicketTestData#provideDomains")
    void deleteById_ExistTicketId_DeleteTicket(Ticket ticket) {
        repository.insert(ticket);

        repository.deleteById(ticket.ticketId());

        Exception exception = catchException(() -> repository.findById(ticket.ticketId()));
        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 티켓을 아이디로 삭제 시 실패한다.")
    @MethodSource("com.tangerine.ticketbox.ticket.TicketTestData#provideDomains")
    void deleteById_NotExistTicketId_Exception(Ticket ticket) {

        repository.deleteById(ticket.ticketId());

        Exception exception = catchException(() -> repository.findById(ticket.ticketId()));
        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @ParameterizedTest
    @DisplayName("모든 티켓을 조회한다.")
    @MethodSource("com.tangerine.ticketbox.ticket.TicketTestData#provideDomains")
    void findAll_Void_ReturnTicketList(Ticket ticket) {
        repository.insert(ticket);

        List<Ticket> result = repository.findAll();

        assertThat(result).isNotEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 티켓을 아이디로 조회 시 성공한다.")
    @MethodSource("com.tangerine.ticketbox.ticket.TicketTestData#provideDomains")
    void findById_ExistTicket_ReturnTicket(Ticket ticket) {
        repository.insert(ticket);

        Ticket result = repository.findById(ticket.ticketId());

        assertThat(result).isEqualTo(ticket);
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 티켓을 아이디로 조회 시 실패한다.")
    @MethodSource("com.tangerine.ticketbox.ticket.TicketTestData#provideDomains")
    void findById_NotExistTicket_ReturnNull(Ticket ticket) {

        Exception exception = catchException(() -> repository.findById(ticket.ticketId()));

        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 티켓을 공연 아이디로 조회 시 성공한다.")
    @MethodSource("com.tangerine.ticketbox.ticket.TicketTestData#provideDomains")
    void findByTheatreId_ExistTicket_ReturnTicket(Ticket ticket) {
        repository.insert(ticket);

        Ticket result = repository.findByTheatreId(ticket.theatreId());

        assertThat(result).isEqualTo(ticket);
    }

    @Test
    @DisplayName("존재하지 않는 티켓을 공연 아이디로 조회 시 실패한다.")
    void findByTheatreId_NotExistTicket_ReturnNull() {

        Exception exception = catchException(() -> repository.findById(TicketTestData.theatreDomains.get(0).theatreId()));

        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

}