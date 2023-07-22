package com.tangerine.theatre_manager.ticket.order.service.mapper;

import com.tangerine.theatre_manager.ticket.order.repository.model.TicketOrderEntity;
import com.tangerine.theatre_manager.ticket.order.service.dto.TicketOrderParam;
import com.tangerine.theatre_manager.ticket.order.service.dto.TicketOrderResult;
import com.tangerine.theatre_manager.ticket.order.service.model.TicketOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TicketOrderServiceMapper {

    TicketOrderServiceMapper INSTANCE = Mappers.getMapper(TicketOrderServiceMapper.class);

    TicketOrder paramToDomain(TicketOrderParam param);

    TicketOrderEntity domainToEntity(TicketOrder domain);

    @Mapping(target = "tickets", ignore = true)
    TicketOrder entityToDomain(TicketOrderEntity entity);

    TicketOrderResult domainToResult(TicketOrder domain);

}
