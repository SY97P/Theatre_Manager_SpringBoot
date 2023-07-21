package com.tangerine.ticketbox.ticket.order.repository;

import com.tangerine.ticketbox.global.exception.SqlException;
import com.tangerine.ticketbox.ticket.order.repository.model.TicketOrderEntity;
import com.tangerine.ticketbox.ticket.order.vo.Email;
import com.tangerine.ticketbox.ticket.order.vo.TicketOrderStatus;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Repository
@Profile({"test", "default"})
public class JdbcTicketOrderRepository implements TicketOrderRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcTicketOrderRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<TicketOrderEntity> orderRowMapper = (resultSet, rowNumber) -> {
        UUID orderId = UUID.fromString(resultSet.getString("order_id"));
        Email email = new Email(resultSet.getString("email"));
        LocalDate orderedAt = resultSet.getDate("ordered_at").toLocalDate();
        TicketOrderStatus orderStatus = TicketOrderStatus.valueOf(resultSet.getString("order_status"));
        return new TicketOrderEntity(orderId, email, orderedAt, orderStatus);
    };

    private static Map<String, Object> toParamMap(TicketOrderEntity order) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("orderId", order.orderId().toString());
        paramMap.put("email", order.email().emailAddress());
        paramMap.put("orderedAt", Date.valueOf(order.orderedAt()));
        paramMap.put("orderStatus", order.ticketOrderStatus().toString());
        return paramMap;
    }

    @Override
    public void insert(TicketOrderEntity order) {
        int result = jdbcTemplate.update(
                "INSERT INTO ticket_orders (order_id, email, ordered_at, order_status)" +
                        " VALUES (:orderId, :email, :orderedAt, :orderStatus)",
                toParamMap(order)
        );
        if (result != 1) {
            throw new SqlException("TicketOrder date is not inserted");
        }
    }

    @Override
    public void update(TicketOrderEntity order) {
        int result = jdbcTemplate.update(
                "UPDATE ticket_orders SET email = :email, ordered_at = :orderedAt, order_status = :orderStatus WHERE order_id = :orderId",
                toParamMap(order)
        );
        if (result != 1) {
            throw new SqlException("TicketOrder data is not updated");
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(
                "DELETE FROM ticket_orders",
                Collections.emptyMap()
        );
    }

    @Override
    public void deleteById(UUID orderId) {
        jdbcTemplate.update(
                "DELETE FROM ticket_orders WHERE order_id = :orderId",
                Collections.singletonMap("orderId", orderId.toString())
        );
    }

    @Override
    public List<TicketOrderEntity> findAll() {
        return jdbcTemplate.query(
                "SELECT order_id, email, ordered_at, order_status FROM ticket_orders",
                orderRowMapper
        );
    }

    @Override
    public TicketOrderEntity findById(UUID orderId) {
        return jdbcTemplate.queryForObject(
                "SELECT order_id, email, ordered_at, order_status FROM ticket_orders WHERE order_id = :orderId",
                Collections.singletonMap("orderId", orderId.toString()),
                orderRowMapper
        );
    }

    @Override
    public TicketOrderEntity findByEmail(Email email) {
        return jdbcTemplate.queryForObject(
                "SELECT order_id, email, ordered_at, order_status FROM ticket_orders WHERE email = :email",
                Collections.singletonMap("email", email.emailAddress()),
                orderRowMapper
        );
    }

}
