package com.tangerine.ticketbox.theatre.service;

import com.tangerine.ticketbox.global.exception.SqlException;
import com.tangerine.ticketbox.theatre.TheatreTestData;
import com.tangerine.ticketbox.theatre.repository.JdbcTheatreRepository;
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
@Import({TheatreService.class, JdbcTheatreRepository.class})
class TheatreServiceTest {

    @Autowired
    TheatreService service;

    @ParameterizedTest
    @DisplayName("존재하지 않는 공연 추가 시 성공한다.")
    @MethodSource("com.tangerine.ticketbox.theatre.TheatreTestData#provideParams")
    void insert_NotExistTheatre_InsertTheatre(TheatreParam theatre) {

        service.createTheatre(theatre);

        TheatreResult result = service.findTheatreById(theatre.theatreId());
        assertThat(result.theatreId()).isEqualTo(theatre.theatreId());
    }

    @ParameterizedTest
    @DisplayName("존재하는 공연 추가 시 실패한다.")
    @MethodSource("com.tangerine.ticketbox.theatre.TheatreTestData#provideParams")
    void insert_ExistTheatre_Exception(TheatreParam theatre) {
        service.createTheatre(theatre);

        Exception exception = catchException(() -> service.createTheatre(theatre));

        assertThat(exception).isInstanceOf(Exception.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 공연 업데이트 시 성공한다.")
    @MethodSource("com.tangerine.ticketbox.theatre.TheatreTestData#provideParams")
    void update_ExistTheatre_UpdateTheatre(TheatreParam theatre) {
        service.createTheatre(theatre);

        service.updateTheatre(TheatreTestData.newParams(theatre));

        TheatreResult result = service.findTheatreById(theatre.theatreId());
        assertThat(result.theatreId()).isEqualTo(TheatreTestData.newParams(theatre).theatreId());
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 공연 업데이트 실패한다.")
    @MethodSource("com.tangerine.ticketbox.theatre.TheatreTestData#provideParams")
    void update_NotExistTheatre_Exception(TheatreParam theatre) {

        Exception exception = catchException(() -> service.updateTheatre(theatre));

        assertThat(exception).isInstanceOf(SqlException.class);
    }

    @Test
    @DisplayName("모든 공연을 삭제 시 성공한다.")
    void deleteAll_Void_DeleteAllTheatre() {
        service.deleteAllTheatre();

        List<TheatreResult> result = service.findAllTheatre();

        assertThat(result).isEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 공연을 아이디로 삭제 시 성공한다.")
    @MethodSource("com.tangerine.ticketbox.theatre.TheatreTestData#provideParams")
    void deleteById_ExistTheatreId_DeleteTheatre(TheatreParam theatre) {
        service.createTheatre(theatre);

        service.deleteTheatreById(theatre.theatreId());

        Exception exception = catchException(() -> service.findTheatreById(theatre.theatreId()));
        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 공연을 아이디로 삭제 시 실패한다.")
    @MethodSource("com.tangerine.ticketbox.theatre.TheatreTestData#provideParams")
    void deleteById_NotExistTheatreId_Exception(TheatreParam theatre) {

        service.deleteTheatreById(theatre.theatreId());

        Exception exception = catchException(() -> service.findTheatreById(theatre.theatreId()));
        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @ParameterizedTest
    @DisplayName("모든 공연을 조회한다.")
    @MethodSource("com.tangerine.ticketbox.theatre.TheatreTestData#provideParams")
    void findAll_Void_ReturnTheatreList(TheatreParam theatre) {
        service.createTheatre(theatre);

        List<TheatreResult> result = service.findAllTheatre();

        assertThat(result).isNotEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 공연을 아이디로 조회 시 성공한다.")
    @MethodSource("com.tangerine.ticketbox.theatre.TheatreTestData#provideParams")
    void findById_ExistTheatre_ReturnTheatre(TheatreParam theatre) {
        service.createTheatre(theatre);

        TheatreResult result = service.findTheatreById(theatre.theatreId());

        assertThat(result.theatreId()).isEqualTo(theatre.theatreId());
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 공연을 아이디로 조회 시 실패한다.")
    @MethodSource("com.tangerine.ticketbox.theatre.TheatreTestData#provideParams")
    void findById_NotExistTheatre_ReturnNull(TheatreParam theatre) {

        Exception exception = catchException(() -> service.findTheatreById(theatre.theatreId()));

        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 공연을 이름으로 조회 시 성공한다.")
    @MethodSource("com.tangerine.ticketbox.theatre.TheatreTestData#provideParams")
    void findByName_ExistTheatre_ReturnTheatre(TheatreParam theatre) {
        service.createTheatre(theatre);

        TheatreResult result = service.findTheatreByName(theatre.theatreName());

        assertThat(result.theatreId()).isEqualTo(theatre.theatreId());
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 공연을 이름으로 조회 시 실패한다.")
    @MethodSource("com.tangerine.ticketbox.theatre.TheatreTestData#provideParams")
    void findByName_NotExistTheatre_ReturnNull(TheatreParam theatre) {

        Exception exception = catchException(() -> service.findTheatreByName(theatre.theatreName()));

        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 공연을 날짜로 조회 시 성공한다.")
    @MethodSource("com.tangerine.ticketbox.theatre.TheatreTestData#provideParams")
    void findByDate_ExistTheatre_ReturnTheatre(TheatreParam theatre) {
        service.createTheatre(theatre);

        TheatreResult result = service.findTheatreByDate(theatre.openRun());

        assertThat(result.theatreId()).isEqualTo(theatre.theatreId());
    }

    @ParameterizedTest
    @DisplayName("존재하는 공연을 날짜로 조회 시 실패한다.")
    @MethodSource("com.tangerine.ticketbox.theatre.TheatreTestData#provideParams")
    void findByDate_NotExistTheatre_Exception(TheatreParam theatre) {

        Exception exception = catchException(() -> service.findTheatreByDate(theatre.closeRun().plusDays(2)));

        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }
}