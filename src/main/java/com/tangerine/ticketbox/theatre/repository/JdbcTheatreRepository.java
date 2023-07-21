package com.tangerine.ticketbox.theatre.repository;

import com.tangerine.ticketbox.global.exception.SqlException;
import com.tangerine.ticketbox.theatre.vo.*;
import com.tangerine.ticketbox.theatre.repository.model.Theatre;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Repository
@Profile({"test", "default"})
public class JdbcTheatreRepository implements TheatreRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcTheatreRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Theatre> theatreRowMapper = (resultSet, rowNumber) -> {
        UUID theatreId = UUID.fromString(resultSet.getString("theatre_id"));
        TheatreName theatreName = new TheatreName(resultSet.getString("theatre_name"));
        Genre genre = Genre.valueOf(resultSet.getString("genre"));
        AgeRate ageRate = AgeRate.valueOf(resultSet.getString("age_rate"));
        LocalDate openRun = resultSet.getDate("open_run").toLocalDate();
        LocalDate closeRun = resultSet.getDate("close_run").toLocalDate();
        Stage stage = Stage.valueOf(resultSet.getString("stage"));
        return new Theatre(theatreId, theatreName, genre, ageRate, openRun, closeRun, stage);
    };

    private static Map<String, Object> toParamMap(Theatre theatre) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("theatreId", theatre.theatreId().toString());
        paramMap.put("theatreName", theatre.theatreName().theatreNameValue());
        paramMap.put("genre", theatre.genre().toString());
        paramMap.put("ageRate", theatre.ageRate().toString());
        paramMap.put("openRun", Date.valueOf(theatre.openRun()));
        paramMap.put("closeRun", Date.valueOf(theatre.closeRun()));
        paramMap.put("stage", theatre.stage().toString());
        return paramMap;
    }

    @Override
    public void insert(Theatre theatre) {
        int result = jdbcTemplate.update(
                "INSERT INTO theatres(theatre_id, theatre_name, genre, age_rate, open_run, close_run, stage)" +
                        " VALUES (:theatreId, :theatreName, :genre, :ageRate, :openRun, :closeRun, :stage)",
                toParamMap(theatre)
        );
        if (result != 1) {
            throw new SqlException("Theatre data is not inserted");
        }
    }

    @Override
    public void update(Theatre theatre) {
        int result = jdbcTemplate.update(
                "UPDATE theatres SET theatre_name = :theatreName, genre = :genre, age_rate = :ageRate, open_run = :openRun, close_run = :closeRun, stage = :stage" +
                        " WHERE theatre_id = :theatreId",
                toParamMap(theatre)
        );
        if (result != 1) {
            throw new SqlException("Theatre data is not updated");
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(
                "DELETE FROM theatres",
                Collections.emptyMap()
        );
    }

    @Override
    public void deleteById(UUID theatreId) {
        jdbcTemplate.update(
                "DELETE FROM theatres WHERE theatre_id = :theatreId",
                Collections.singletonMap("theatreId", theatreId.toString())
        );
    }

    @Override
    public List<Theatre> findAll() {
        return jdbcTemplate.query(
                "SELECT theatre_id, theatre_name, genre, age_rate, open_run, close_run, stage FROM theatres",
                theatreRowMapper
        );
    }

    @Override
    public Theatre findById(UUID theatreId) {
        return jdbcTemplate.queryForObject(
                "SELECT theatre_id, theatre_name, genre, age_rate, open_run, close_run, stage" +
                        " FROM theatres WHERE theatre_id = :theatreId",
                Collections.singletonMap("theatreId", theatreId.toString()),
                theatreRowMapper);
    }

    @Override
    public Theatre findByName(TheatreName theatreName) {
        return jdbcTemplate.queryForObject(
                "SELECT theatre_id, theatre_name, genre, age_rate, open_run, close_run, stage" +
                        " FROM theatres WHERE theatre_name = :theatreName",
                Collections.singletonMap("theatreName", theatreName.theatreNameValue()),
                theatreRowMapper);
    }

    @Override
    public Theatre findByDate(LocalDate date) {
        return jdbcTemplate.queryForObject(
                "SELECT theatre_id, theatre_name, genre, age_rate, open_run, close_run, stage" +
                        " FROM theatres WHERE :date BETWEEN open_run AND close_run",
                Collections.singletonMap("date", date),
                theatreRowMapper);
    }
}
