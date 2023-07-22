package com.tangerine.theatre_manager.performance.repository;

import com.tangerine.theatre_manager.global.exception.SqlException;
import com.tangerine.theatre_manager.performance.repository.model.Performance;
import com.tangerine.theatre_manager.performance.vo.AgeRate;
import com.tangerine.theatre_manager.performance.vo.Genre;
import com.tangerine.theatre_manager.performance.vo.PerformanceName;
import com.tangerine.theatre_manager.performance.vo.Stage;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Repository
@Profile({"test", "default"})
public class JdbcPerformanceRepository implements PerformanceRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcPerformanceRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Performance> performanceRowMapper = (resultSet, rowNumber) -> {
        UUID performanceId = UUID.fromString(resultSet.getString("performance_id"));
        PerformanceName performanceName = new PerformanceName(resultSet.getString("performance_name"));
        Genre genre = Genre.valueOf(resultSet.getString("genre"));
        AgeRate ageRate = AgeRate.valueOf(resultSet.getString("age_rate"));
        LocalDate openRun = resultSet.getDate("open_run").toLocalDate();
        LocalDate closeRun = resultSet.getDate("close_run").toLocalDate();
        Stage stage = Stage.valueOf(resultSet.getString("stage"));
        return new Performance(performanceId, performanceName, genre, ageRate, openRun, closeRun, stage);
    };

    private static Map<String, Object> toParamMap(Performance performance) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("performanceId", performance.performanceId().toString());
        paramMap.put("performanceName", performance.performanceName().performanceNameValue());
        paramMap.put("genre", performance.genre().toString());
        paramMap.put("ageRate", performance.ageRate().toString());
        paramMap.put("openRun", Date.valueOf(performance.openRun()));
        paramMap.put("closeRun", Date.valueOf(performance.closeRun()));
        paramMap.put("stage", performance.stage().toString());
        return paramMap;
    }

    @Override
    public void insert(Performance performance) {
        int result = jdbcTemplate.update(
                "INSERT INTO performances(performance_id, performance_name, genre, age_rate, open_run, close_run, stage)" +
                        " VALUES (:performanceId, :performanceName, :genre, :ageRate, :openRun, :closeRun, :stage)",
                toParamMap(performance)
        );
        if (result != 1) {
            throw new SqlException("Performance data is not inserted");
        }
    }

    @Override
    public void update(Performance performance) {
        int result = jdbcTemplate.update(
                "UPDATE performances SET performance_name = :performanceName, genre = :genre, age_rate = :ageRate, open_run = :openRun, close_run = :closeRun, stage = :stage" +
                        " WHERE performance_id = :performanceId",
                toParamMap(performance)
        );
        if (result != 1) {
            throw new SqlException("Performance data is not updated");
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(
                "DELETE FROM performances",
                Collections.emptyMap()
        );
    }

    @Override
    public void deleteById(UUID performanceId) {
        jdbcTemplate.update(
                "DELETE FROM performances WHERE performance_id = :performanceId",
                Collections.singletonMap("performanceId", performanceId.toString())
        );
    }

    @Override
    public List<Performance> findAll() {
        return jdbcTemplate.query(
                "SELECT performance_id, performance_name, genre, age_rate, open_run, close_run, stage FROM performances",
                performanceRowMapper
        );
    }

    @Override
    public Performance findById(UUID performanceId) {
        return jdbcTemplate.queryForObject(
                "SELECT performance_id, performance_name, genre, age_rate, open_run, close_run, stage" +
                        " FROM performances WHERE performance_id = :performanceId",
                Collections.singletonMap("performanceId", performanceId.toString()),
                performanceRowMapper);
    }

    @Override
    public Performance findByName(PerformanceName performanceName) {
        return jdbcTemplate.queryForObject(
                "SELECT performance_id, performance_name, genre, age_rate, open_run, close_run, stage" +
                        " FROM performances WHERE performance_name = :performanceName",
                Collections.singletonMap("performanceName", performanceName.performanceNameValue()),
                performanceRowMapper);
    }

    @Override
    public Performance findByDate(LocalDate date) {
        return jdbcTemplate.queryForObject(
                "SELECT performance_id, performance_name, genre, age_rate, open_run, close_run, stage" +
                        " FROM performances WHERE :date BETWEEN open_run AND close_run",
                Collections.singletonMap("date", date),
                performanceRowMapper);
    }
}
