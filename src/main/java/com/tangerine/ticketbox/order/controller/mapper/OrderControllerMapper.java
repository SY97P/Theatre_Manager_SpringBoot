package com.tangerine.ticketbox.order.controller.mapper;

import com.tangerine.ticketbox.order.controller.dto.CreateOrderRequest;
import com.tangerine.ticketbox.order.controller.dto.OrderResponse;
import com.tangerine.ticketbox.order.controller.dto.UpdateOrderRequest;
import com.tangerine.ticketbox.order.model.Email;
import com.tangerine.ticketbox.order.model.OrderStatus;
import com.tangerine.ticketbox.order.service.dto.OrderParam;
import com.tangerine.ticketbox.order.service.dto.OrderResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import javax.swing.plaf.synth.SynthOptionPaneUI;

import static com.tangerine.ticketbox.order.model.OrderStatus.ACCEPTED;

@Mapper(componentModel = "spring")
public interface OrderControllerMapper {

    OrderControllerMapper INSTANCE = Mappers.getMapper(OrderControllerMapper.class);

    @Mapping(target = "orderId", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "orderedAt", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "orderStatus", expression = "java(OrderStatus.ACCEPTED)")
    OrderParam requestToParam(CreateOrderRequest request);

    OrderParam requestToParam(UpdateOrderRequest request);

    OrderResponse resultToResponse(OrderResult result);

}
