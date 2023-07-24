package com.tangerine.theatre_manager.order.ticket.repository;

import com.tangerine.theatre_manager.global.exception.SqlException;
import com.tangerine.theatre_manager.order.ticket.model.Ticket;
import com.tangerine.theatre_manager.performance.vo.Price;
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

    private static final RowMapper<Ticket> ticketRowMapper = (resultSet, rowNumber) -> {
        UUID ticketId = UUID.fromString(resultSet.getString("ticket_id"));
        UUID orderId = UUID.fromString(resultSet.getString("order_id"));
        Price ticketPrice = new Price(resultSet.getLong("ticket_price"));
        LocalDate reservedDate = resultSet.getDate("reserved_date").toLocalDate();
        return new Ticket(ticketId, orderId, ticketPrice, reservedDate);
    };

    private static Map<String, Object> toParamMap(Ticket ticket) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("ticketId", ticket.ticketId().toString());
        paramMap.put("orderId", ticket.orderId().toString());
        paramMap.put("ticketPrice", ticket.ticketPrice().priceValue());
        paramMap.put("reservedDate", Date.valueOf(ticket.reservedDate()));
        return paramMap;
    }

    @Override
    public void insert(Ticket ticket) {
        int result = jdbcTemplate.update(
                "INSERT INTO tickets (ticket_id, order_id, ticket_price, reserved_date)" +
                        " VALUES (:ticketId, :orderId, :ticketPrice, :reservedDate)",
                toParamMap(ticket)
        );
        if (result != 1) {
            throw new SqlException("TicketOrderEntity data is not inserted");
        }
    }

    @Override
    public void deleteByOrderId(UUID orderId) {
        jdbcTemplate.update(
                "DELETE FROM tickets WHERE order_id = :orderId",
                Collections.singletonMap("orderId", orderId.toString())
        );
    }

    @Override
    public List<Ticket> findByOrderId(UUID orderId) {
        return jdbcTemplate.query(
                "SELECT ticket_id, order_id, ticket_price, reserved_date FROM tickets WHERE order_id = :orderId",
                Collections.singletonMap("orderId", orderId.toString()),
                ticketRowMapper
        );
    }

}
