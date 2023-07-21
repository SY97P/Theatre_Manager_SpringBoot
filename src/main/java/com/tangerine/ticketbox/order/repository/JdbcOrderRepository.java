package com.tangerine.ticketbox.order.repository;

import com.tangerine.ticketbox.global.exception.SqlException;
import com.tangerine.ticketbox.order.model.Email;
import com.tangerine.ticketbox.order.model.OrderStatus;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Repository
@Profile({"test", "default"})
public class JdbcOrderRepository implements OrderRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcOrderRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Order> orderRowMapper = (resultSet, rowNumber) -> {
        UUID orderId = UUID.fromString(resultSet.getString("order_id"));
        Email email = new Email(resultSet.getString("email"));
        LocalDate orderedAt = resultSet.getDate("ordered_at").toLocalDate();
        OrderStatus orderStatus = OrderStatus.valueOf(resultSet.getString("order_status"));
        return new Order(orderId, email, orderedAt, orderStatus);
    };

    private static Map<String, Object> toParamMap(Order order) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("orderId", order.orderId().toString());
        paramMap.put("email", order.email().emailAddress());
        paramMap.put("orderedAt", Date.valueOf(order.orderedAt()));
        paramMap.put("orderStatus", order.orderStatus().toString());
        return paramMap;
    }

    @Override
    public void insert(Order order) {
        int result = jdbcTemplate.update(
                "INSERT INTO orders (order_id, email, ordered_at, order_status)" +
                        " VALUES (:orderId, :email, :orderedAt, :orderStatus)",
                toParamMap(order)
        );
        if (result != 1) {
            throw new SqlException("Order date is not inserted");
        }
    }

    @Override
    public void update(Order order) {
        int result = jdbcTemplate.update(
                "UPDATE orders SET email = :email, ordered_at = :orderedAt, order_status = :orderStatus WHERE order_id = :orderId",
                toParamMap(order)
        );
        if (result != 1) {
            throw new SqlException("Order data is not updated");
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(
                "DELETE FROM orders",
                Collections.emptyMap()
        );
    }

    @Override
    public void deleteById(UUID orderId) {
        jdbcTemplate.update(
                "DELETE FROM orders WHERE order_id = :orderId",
                Collections.singletonMap("orderId", orderId.toString())
        );
    }

    @Override
    public List<Order> findAll() {
        return jdbcTemplate.query(
                "SELECT order_id, email, ordered_at, order_status FROM orders",
                orderRowMapper
        );
    }

    @Override
    public Order findById(UUID orderId) {
        return jdbcTemplate.queryForObject(
                "SELECT order_id, email, ordered_at, order_status FROM orders WHERE order_id = :orderId",
                Collections.singletonMap("orderId", orderId.toString()),
                orderRowMapper
        );
    }

    @Override
    public Order findByEmail(Email email) {
        return jdbcTemplate.queryForObject(
                "SELECT order_id, email, ordered_at, order_status FROM orders WHERE email = :email",
                Collections.singletonMap("email", email.emailAddress()),
                orderRowMapper
        );
    }

}
