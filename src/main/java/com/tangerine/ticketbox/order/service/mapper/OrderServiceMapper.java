package com.tangerine.ticketbox.order.service.mapper;

import com.tangerine.ticketbox.order.repository.Order;
import com.tangerine.ticketbox.order.service.dto.OrderParam;
import com.tangerine.ticketbox.order.service.dto.OrderResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderServiceMapper {

    OrderServiceMapper INSTANCE = Mappers.getMapper(OrderServiceMapper.class);

    Order paramToDomain(OrderParam param);

    OrderResult domainToResult(Order domain);

}
