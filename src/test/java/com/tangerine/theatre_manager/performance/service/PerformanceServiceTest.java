package com.tangerine.theatre_manager.performance.service;

import com.tangerine.theatre_manager.global.exception.SqlException;
import com.tangerine.theatre_manager.performance.PerformanceTestData;
import com.tangerine.theatre_manager.performance.repository.JdbcPerformanceRepository;
import com.tangerine.theatre_manager.performance.service.dto.PerformanceParam;
import com.tangerine.theatre_manager.performance.service.dto.PerformanceResult;
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
@Import({PerformanceService.class, JdbcPerformanceRepository.class})
class PerformanceServiceTest {

    @Autowired
    PerformanceService service;

    @ParameterizedTest
    @DisplayName("존재하지 않는 공연 추가 시 성공한다.")
    @MethodSource("com.tangerine.theatre_manager.performance.PerformanceTestData#provideParams")
    void insert_NotExistPerformance_InsertPerformance(PerformanceParam performance) {

        service.createPerformance(performance);

        PerformanceResult result = service.findPerformanceById(performance.performanceId());
        assertThat(result.performanceId()).isEqualTo(performance.performanceId());
    }

    @ParameterizedTest
    @DisplayName("존재하는 공연 추가 시 실패한다.")
    @MethodSource("com.tangerine.theatre_manager.performance.PerformanceTestData#provideParams")
    void insert_ExistPerformance_Exception(PerformanceParam performance) {
        service.createPerformance(performance);

        Exception exception = catchException(() -> service.createPerformance(performance));

        assertThat(exception).isInstanceOf(Exception.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 공연 업데이트 시 성공한다.")
    @MethodSource("com.tangerine.theatre_manager.performance.PerformanceTestData#provideParams")
    void update_ExistPerformance_UpdatePerformance(PerformanceParam performance) {
        service.createPerformance(performance);

        service.updatePerformance(PerformanceTestData.newParams(performance));

        PerformanceResult result = service.findPerformanceById(performance.performanceId());
        assertThat(result.performanceId()).isEqualTo(PerformanceTestData.newParams(performance).performanceId());
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 공연 업데이트 실패한다.")
    @MethodSource("com.tangerine.theatre_manager.performance.PerformanceTestData#provideParams")
    void update_NotExistPerformance_Exception(PerformanceParam performance) {

        Exception exception = catchException(() -> service.updatePerformance(performance));

        assertThat(exception).isInstanceOf(SqlException.class);
    }

    @Test
    @DisplayName("모든 공연을 삭제 시 성공한다.")
    void deleteAll_Void_DeleteAllPerformance() {
        service.deleteAllPerformance();

        List<PerformanceResult> result = service.findAllPerformance();

        assertThat(result).isEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 공연을 아이디로 삭제 시 성공한다.")
    @MethodSource("com.tangerine.theatre_manager.performance.PerformanceTestData#provideParams")
    void deleteById_ExistPerformanceId_DeletePerformance(PerformanceParam performance) {
        service.createPerformance(performance);

        service.deletePerformanceById(performance.performanceId());

        Exception exception = catchException(() -> service.findPerformanceById(performance.performanceId()));
        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 공연을 아이디로 삭제 시 실패한다.")
    @MethodSource("com.tangerine.theatre_manager.performance.PerformanceTestData#provideParams")
    void deleteById_NotExistPerformanceId_Exception(PerformanceParam performance) {

        service.deletePerformanceById(performance.performanceId());

        Exception exception = catchException(() -> service.findPerformanceById(performance.performanceId()));
        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @ParameterizedTest
    @DisplayName("모든 공연을 조회한다.")
    @MethodSource("com.tangerine.theatre_manager.performance.PerformanceTestData#provideParams")
    void findAll_Void_ReturnPerformanceList(PerformanceParam performance) {
        service.createPerformance(performance);

        List<PerformanceResult> result = service.findAllPerformance();

        assertThat(result).isNotEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 공연을 아이디로 조회 시 성공한다.")
    @MethodSource("com.tangerine.theatre_manager.performance.PerformanceTestData#provideParams")
    void findById_ExistPerformance_ReturnPerformance(PerformanceParam performance) {
        service.createPerformance(performance);

        PerformanceResult result = service.findPerformanceById(performance.performanceId());

        assertThat(result.performanceId()).isEqualTo(performance.performanceId());
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 공연을 아이디로 조회 시 실패한다.")
    @MethodSource("com.tangerine.theatre_manager.performance.PerformanceTestData#provideParams")
    void findById_NotExistPerformance_ReturnNull(PerformanceParam performance) {

        Exception exception = catchException(() -> service.findPerformanceById(performance.performanceId()));

        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 공연을 이름으로 조회 시 성공한다.")
    @MethodSource("com.tangerine.theatre_manager.performance.PerformanceTestData#provideParams")
    void findByName_ExistPerformance_ReturnPerformance(PerformanceParam performance) {
        service.createPerformance(performance);

        PerformanceResult result = service.findPerformanceByName(performance.performanceName());

        assertThat(result.performanceId()).isEqualTo(performance.performanceId());
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 공연을 이름으로 조회 시 실패한다.")
    @MethodSource("com.tangerine.theatre_manager.performance.PerformanceTestData#provideParams")
    void findByName_NotExistPerformance_ReturnNull(PerformanceParam performance) {

        Exception exception = catchException(() -> service.findPerformanceByName(performance.performanceName()));

        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 공연을 날짜로 조회 시 성공한다.")
    @MethodSource("com.tangerine.theatre_manager.performance.PerformanceTestData#provideParams")
    void findByDate_ExistPerformance_ReturnPerformance(PerformanceParam performance) {
        service.createPerformance(performance);

        PerformanceResult result = service.findPerformanceByDate(performance.openRun());

        assertThat(result.performanceId()).isEqualTo(performance.performanceId());
    }

    @ParameterizedTest
    @DisplayName("존재하는 공연을 날짜로 조회 시 실패한다.")
    @MethodSource("com.tangerine.theatre_manager.performance.PerformanceTestData#provideParams")
    void findByDate_NotExistPerformance_Exception(PerformanceParam performance) {

        Exception exception = catchException(() -> service.findPerformanceByDate(performance.closeRun().plusDays(2)));

        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

}