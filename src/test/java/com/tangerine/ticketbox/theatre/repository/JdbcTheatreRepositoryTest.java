package com.tangerine.ticketbox.theatre.repository;

import com.tangerine.ticketbox.global.exception.SqlException;
import com.tangerine.ticketbox.theatre.TheatreTestData;
import com.tangerine.ticketbox.theatre.model.AgeRate;
import com.tangerine.ticketbox.theatre.model.Genre;
import com.tangerine.ticketbox.theatre.model.Theatre;
import com.tangerine.ticketbox.theatre.model.TheatreName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@ActiveProfiles("test")
@Import(JdbcTheatreRepository.class)
class JdbcTheatreRepositoryTest {

    @Autowired
    JdbcTheatreRepository repository;

    @ParameterizedTest
    @DisplayName("존재하지 않는 공연 추가 시 성공한다.")
    @MethodSource("com.tangerine.ticketbox.theatre.TheatreTestData#provideTheatres")
    void insert_NotExistTheatre_InsertTheatre(Theatre theatre) {

        repository.insert(theatre);

        Theatre result = repository.findById(theatre.theatreId());
        assertThat(result).isEqualTo(theatre);
    }

    @ParameterizedTest
    @DisplayName("존재하는 공연 추가 시 실패한다.")
    @MethodSource("com.tangerine.ticketbox.theatre.TheatreTestData#provideTheatres")
    void insert_ExistTheatre_Exception(Theatre theatre) {
        repository.insert(theatre);

        Exception exception = catchException(() -> repository.insert(theatre));

        assertThat(exception).isInstanceOf(Exception.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 공연 업데이트 시 성공한다.")
    @MethodSource("com.tangerine.ticketbox.theatre.TheatreTestData#provideTheatres")
    void update_ExistTheatre_UpdateTheatre(Theatre theatre) {
        repository.insert(theatre);

        repository.update(TheatreTestData.newTheatre(theatre));

        Theatre result = repository.findById(theatre.theatreId());
        assertThat(result).isEqualTo(TheatreTestData.newTheatre(theatre));
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 공연 업데이트 실패한다.")
    @MethodSource("com.tangerine.ticketbox.theatre.TheatreTestData#provideTheatres")
    void update_NotExistTheatre_Exception(Theatre theatre) {

        Exception exception = catchException(() -> repository.update(theatre));

        assertThat(exception).isInstanceOf(SqlException.class);
    }

    @Test
    @DisplayName("모든 공연을 삭제 시 성공한다.")
    void deleteAll_Void_DeleteAllTheatre() {
        repository.deleteAll();

        List<Theatre> result = repository.findAll();

        assertThat(result).isEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 공연을 아이디로 삭제 시 성공한다.")
    @MethodSource("com.tangerine.ticketbox.theatre.TheatreTestData#provideTheatres")
    void deleteById_ExistTheatreId_DeleteTheatre(Theatre theatre) {
        repository.insert(theatre);

        repository.deleteById(theatre.theatreId());

        Exception exception = catchException(() -> repository.findById(theatre.theatreId()));
        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 공연을 아이디로 삭제 시 실패한다.")
    @MethodSource("com.tangerine.ticketbox.theatre.TheatreTestData#provideTheatres")
    void deleteById_NotExistTheatreId_Exception(Theatre theatre) {

        repository.deleteById(theatre.theatreId());

        Exception exception = catchException(() -> repository.findById(theatre.theatreId()));
        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @ParameterizedTest
    @DisplayName("모든 공연을 조회한다.")
    @MethodSource("com.tangerine.ticketbox.theatre.TheatreTestData#provideTheatres")
    void findAll_Void_ReturnTheatreList(Theatre theatre) {
        repository.insert(theatre);

        List<Theatre> result = repository.findAll();

        assertThat(result).isNotEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 공연을 아이디로 조회 시 성공한다.")
    @MethodSource("com.tangerine.ticketbox.theatre.TheatreTestData#provideTheatres")
    void findById_ExistTheatre_ReturnTheatre(Theatre theatre) {
        repository.insert(theatre);

        Theatre result = repository.findById(theatre.theatreId());

        assertThat(result).isEqualTo(theatre);
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 공연을 아이디로 조회 시 실패한다.")
    @MethodSource("com.tangerine.ticketbox.theatre.TheatreTestData#provideTheatres")
    void findById_NotExistTheatre_ReturnNull(Theatre theatre) {

        Exception exception = catchException(() -> repository.findById(theatre.theatreId()));

        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 공연을 이름으로 조회 시 성공한다.")
    @MethodSource("com.tangerine.ticketbox.theatre.TheatreTestData#provideTheatres")
    void findByName_ExistTheatre_ReturnTheatre(Theatre theatre) {
        repository.insert(theatre);

        Theatre result = repository.findByName(theatre.theatreName());

        assertThat(result).isEqualTo(theatre);
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 공연을 이름으로 조회 시 실패한다.")
    @MethodSource("com.tangerine.ticketbox.theatre.TheatreTestData#provideTheatres")
    void findByName_NotExistTheatre_ReturnNull(Theatre theatre) {

        Exception exception = catchException(() -> repository.findByName(theatre.theatreName()));

        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 공연을 날짜로 조회 시 성공한다.")
    @MethodSource("com.tangerine.ticketbox.theatre.TheatreTestData#provideTheatres")
    void findByDate_ExistTheatre_ReturnTheatre(Theatre theatre) {
        repository.insert(theatre);

        Theatre result = repository.findByDate(theatre.openRun());

        assertThat(result).isEqualTo(theatre);
    }

    @ParameterizedTest
    @DisplayName("존재하는 공연을 날짜로 조회 시 실패한다.")
    @MethodSource("com.tangerine.ticketbox.theatre.TheatreTestData#provideTheatres")
    void findByDate_NotExistTheatre_Exception(Theatre theatre) {

        Exception exception = catchException(() -> repository.findByDate(theatre.closeRun().plusDays(2)));

        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

}