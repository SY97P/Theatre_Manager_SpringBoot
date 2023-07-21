package com.tangerine.ticketbox.order;

import com.tangerine.ticketbox.order.model.Email;
import com.tangerine.ticketbox.order.model.OrderStatus;
import com.tangerine.ticketbox.order.repository.Order;
import com.tangerine.ticketbox.order.service.dto.OrderParam;
import com.tangerine.ticketbox.order.service.mapper.OrderServiceMapper;
import org.junit.jupiter.params.provider.Arguments;
import org.mockito.internal.matchers.Or;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class OrderTestData {

    public static OrderParam newParams(OrderParam param) {
        return new OrderParam(param.orderId(), new Email("new-order@naver.com"), LocalDate.now(), OrderStatus.ACCEPTED);
    }

    public static Order newDomain(Order domain) {
        return new Order(domain.orderId(), new Email("new-order@naver.com"), LocalDate.now(), OrderStatus.ACCEPTED);
    }

    public static List<OrderParam> orderParams = List.of(
            new OrderParam(UUID.randomUUID(), new Email("apple@naver.com"), LocalDate.now(), OrderStatus.ACCEPTED),
            new OrderParam(UUID.randomUUID(), new Email("strawberry@naver.com"), LocalDate.now(), OrderStatus.RESERVED),
            new OrderParam(UUID.randomUUID(), new Email("grape@naver.com"), LocalDate.now(), OrderStatus.PAYMENT_CONFIRMED),
            new OrderParam(UUID.randomUUID(), new Email("peach@naver.com"), LocalDate.now(), OrderStatus.ACCEPTED)
    );

    public static Stream<Arguments> provideParams() {
        return orderParams.stream()
                .map(Arguments::of);
    }

    public static Stream<Arguments> provideDomains() {
        return orderParams.stream()
                .map(OrderServiceMapper.INSTANCE::paramToDomain)
                .map(Arguments::of);
    }

}
