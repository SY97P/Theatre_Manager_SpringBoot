package com.tangerine.theatre_manager.performance.repository;

import com.tangerine.theatre_manager.global.exception.SqlException;
import com.tangerine.theatre_manager.performance.PerformanceTestData;
import com.tangerine.theatre_manager.performance.repository.model.Performance;
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
@Import(JdbcPerformanceRepository.class)
class JdbcPerformanceRepositoryTest {

    @Autowired
    JdbcPerformanceRepository repository;

    @ParameterizedTest
    @DisplayName("존재하지 않는 공연 추가 시 성공한다.")
    @MethodSource("com.tangerine.theatre_manager.performance.PerformanceTestData#provideDomains")
    void insert_NotExistPerformance_InsertPerformance(Performance performance) {

        repository.insert(performance);

        Performance result = repository.findById(performance.performanceId());
        assertThat(result).isEqualTo(performance);
    }

    @ParameterizedTest
    @DisplayName("존재하는 공연 추가 시 실패한다.")
    @MethodSource("com.tangerine.theatre_manager.performance.PerformanceTestData#provideDomains")
    void insert_ExistPerformance_Exception(Performance performance) {
        repository.insert(performance);

        Exception exception = catchException(() -> repository.insert(performance));

        assertThat(exception).isInstanceOf(Exception.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 공연 업데이트 시 성공한다.")
    @MethodSource("com.tangerine.theatre_manager.performance.PerformanceTestData#provideDomains")
    void update_ExistPerformance_UpdatePerformance(Performance performance) {
        repository.insert(performance);

        repository.update(PerformanceTestData.newDomain(performance));

        Performance result = repository.findById(performance.performanceId());
        assertThat(result).isEqualTo(PerformanceTestData.newDomain(performance));
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 공연 업데이트 실패한다.")
    @MethodSource("com.tangerine.theatre_manager.performance.PerformanceTestData#provideDomains")
    void update_NotExistPerformance_Exception(Performance performance) {

        Exception exception = catchException(() -> repository.update(performance));

        assertThat(exception).isInstanceOf(SqlException.class);
    }

    @Test
    @DisplayName("모든 공연을 삭제 시 성공한다.")
    void deleteAll_Void_DeleteAllPerformance() {
        repository.deleteAll();

        List<Performance> result = repository.findAll();

        assertThat(result).isEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 공연을 아이디로 삭제 시 성공한다.")
    @MethodSource("com.tangerine.theatre_manager.performance.PerformanceTestData#provideDomains")
    void deleteById_ExistPerformanceId_DeletePerformance(Performance performance) {
        repository.insert(performance);

        repository.deleteById(performance.performanceId());

        Exception exception = catchException(() -> repository.findById(performance.performanceId()));
        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 공연을 아이디로 삭제 시 실패한다.")
    @MethodSource("com.tangerine.theatre_manager.performance.PerformanceTestData#provideDomains")
    void deleteById_NotExistPerformanceId_Exception(Performance performance) {

        repository.deleteById(performance.performanceId());

        Exception exception = catchException(() -> repository.findById(performance.performanceId()));
        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @ParameterizedTest
    @DisplayName("모든 공연을 조회한다.")
    @MethodSource("com.tangerine.theatre_manager.performance.PerformanceTestData#provideDomains")
    void findAll_Void_ReturnPerformanceList(Performance performance) {
        repository.insert(performance);

        List<Performance> result = repository.findAll();

        assertThat(result).isNotEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 공연을 아이디로 조회 시 성공한다.")
    @MethodSource("com.tangerine.theatre_manager.performance.PerformanceTestData#provideDomains")
    void findById_ExistPerformance_ReturnPerformance(Performance performance) {
        repository.insert(performance);

        Performance result = repository.findById(performance.performanceId());

        assertThat(result).isEqualTo(performance);
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 공연을 아이디로 조회 시 실패한다.")
    @MethodSource("com.tangerine.theatre_manager.performance.PerformanceTestData#provideDomains")
    void findById_NotExistPerformance_ReturnNull(Performance performance) {

        Exception exception = catchException(() -> repository.findById(performance.performanceId()));

        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 공연을 이름으로 조회 시 성공한다.")
    @MethodSource("com.tangerine.theatre_manager.performance.PerformanceTestData#provideDomains")
    void findByName_ExistPerformance_ReturnPerformance(Performance performance) {
        repository.insert(performance);

        Performance result = repository.findByName(performance.performanceName());

        assertThat(result).isEqualTo(performance);
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 공연을 이름으로 조회 시 실패한다.")
    @MethodSource("com.tangerine.theatre_manager.performance.PerformanceTestData#provideDomains")
    void findByName_NotExistPerformance_ReturnNull(Performance performance) {

        Exception exception = catchException(() -> repository.findByName(performance.performanceName()));

        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 공연을 날짜로 조회 시 성공한다.")
    @MethodSource("com.tangerine.theatre_manager.performance.PerformanceTestData#provideDomains")
    void findByDate_ExistPerformance_ReturnPerformance(Performance performance) {
        repository.insert(performance);

        Performance result = repository.findByDate(performance.openRun());

        assertThat(result).isEqualTo(performance);
    }

    @ParameterizedTest
    @DisplayName("존재하는 공연을 날짜로 조회 시 실패한다.")
    @MethodSource("com.tangerine.theatre_manager.performance.PerformanceTestData#provideDomains")
    void findByDate_NotExistPerformance_Exception(Performance performance) {

        Exception exception = catchException(() -> repository.findByDate(performance.closeRun().plusDays(2)));

        assertThat(exception).isInstanceOf(EmptyResultDataAccessException.class);
    }

}