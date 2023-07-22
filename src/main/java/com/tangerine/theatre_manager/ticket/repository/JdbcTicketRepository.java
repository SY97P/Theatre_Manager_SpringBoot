package com.tangerine.theatre_manager.ticket.repository;

import com.tangerine.theatre_manager.global.exception.SqlException;
import com.tangerine.theatre_manager.ticket.repository.dto.TicketEntity;
import com.tangerine.theatre_manager.ticket.vo.Price;
import com.tangerine.theatre_manager.ticket.vo.Quantity;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Repository
@Profile({"test", "default"})
public class JdbcTicketRepository implements TicketRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcTicketRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<TicketEntity> ticketRowMapper = (resultSet, rowNumber) -> {
        UUID ticketId = UUID.fromString(resultSet.getString("ticket_id"));
        UUID orderId = UUID.fromString(resultSet.getString("order_id"));
        UUID performanceId = UUID.fromString(resultSet.getString("performance_id"));
        Price ticketPrice = new Price(resultSet.getLong("ticket_price"));
        Quantity ticketQuantity = new Quantity(resultSet.getLong("ticket_quantity"));
        LocalDate reservedDate = resultSet.getDate("reserved_date").toLocalDate();
        return new TicketEntity(ticketId, orderId, performanceId, ticketPrice, ticketQuantity, reservedDate);
    };

    private static Map<String, Object> toParamMap(TicketEntity ticket) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("ticketId", ticket.ticketId().toString());
        paramMap.put("orderId", ticket.orderId().toString());
        paramMap.put("performanceId", ticket.performanceId().toString());
        paramMap.put("ticketPrice", ticket.ticketPrice().priceValue());
        paramMap.put("ticketQuantity", ticket.ticketQuantity().quantityValue());
        paramMap.put("reservedDate", Date.valueOf(ticket.reservedDate()));
        return paramMap;
    }

    @Override
    public void insert(TicketEntity ticket) {
        int result = jdbcTemplate.update(
                "INSERT INTO tickets (ticket_id, order_id, performance_id, ticket_price, ticket_quantity, reserved_date)" +
                        " VALUES (:ticketId, :orderId, :performanceId, :ticketPrice, :ticketQuantity, :reservedDate)",
                toParamMap(ticket)
        );
        if (result != 1) {
            throw new SqlException("TicketEntity data is not inserted");
        }
    }

    @Override
    public void update(TicketEntity ticket) {
        int result = jdbcTemplate.update(
                "UPDATE tickets SET order_id = :orderId, performance_id = :performanceId, ticket_price = :ticketPrice, ticket_quantity = :ticketQuantity, reserved_date = :reservedDate" +
                        " WHERE ticket_id = :ticketId",
                toParamMap(ticket)
        );
        if (result != 1) {
            throw new SqlException("TicketEntity date is not updated");
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(
                "DELETE FROM tickets",
                Collections.emptyMap()
        );
    }

    @Override
    public void deleteById(UUID ticketId) {
        jdbcTemplate.update(
                "DELETE FROM tickets WHERE ticket_id = :ticketId",
                Collections.singletonMap("ticketId", ticketId.toString())
        );
    }

    @Override
    public void deleteByOrderId(UUID orderId) {
        jdbcTemplate.update(
                "DELETE FROM tickets WHERE order_id = :orderId",
                Collections.singletonMap("orderId", orderId.toString())
        );
    }

    @Override
    public List<TicketEntity> findAll() {
        return jdbcTemplate.query(
                "SELECT ticket_id, order_id, performance_id, ticket_price, ticket_quantity, reserved_date FROM tickets",
                ticketRowMapper
        );
    }

    @Override
    public TicketEntity findById(UUID ticketId) {
        return jdbcTemplate.queryForObject(
                "SELECT ticket_id, order_id, performance_id, ticket_price, ticket_quantity, reserved_date" +
                        " FROM tickets WHERE ticket_id = :ticketId",
                Collections.singletonMap("ticketId", ticketId.toString()),
                ticketRowMapper
        );
    }

    @Override
    public TicketEntity findByPerformanceId(UUID performanceId) {
        return jdbcTemplate.queryForObject(
                "SELECT ticket_id, order_id, performance_id, ticket_price, ticket_quantity, reserved_date FROM tickets WHERE performance_id = :performanceId",
                Collections.singletonMap("performanceId", performanceId.toString()),
                ticketRowMapper
        );
    }

    @Override
    public List<TicketEntity> findByOrderId(UUID orderId) {
        return jdbcTemplate.query(
                "SELECT ticket_id, order_id, performance_id, ticket_price, ticket_quantity, reserved_date FROM tickets WHERE order_id = :orderId",
                Collections.singletonMap("orderId", orderId.toString()),
                ticketRowMapper
        );
    }

}