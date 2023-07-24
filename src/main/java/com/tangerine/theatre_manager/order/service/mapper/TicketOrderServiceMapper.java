package com.tangerine.theatre_manager.order.service.mapper;

import com.tangerine.theatre_manager.order.repository.model.TicketOrderEntity;
import com.tangerine.theatre_manager.order.service.model.TicketOrder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TicketOrderServiceMapper {

    TicketOrderServiceMapper INSTANCE = Mappers.getMapper(TicketOrderServiceMapper.class);

    TicketOrderEntity domainToEntity(TicketOrder domain);

}
