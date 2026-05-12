package com.mahendra.tickets.mappers;

import com.mahendra.tickets.domain.CreateEventRequest;
import com.mahendra.tickets.domain.CreateTicketTypeRequest;
import com.mahendra.tickets.domain.dtos.CreateEventRequestDto;
import com.mahendra.tickets.domain.dtos.CreateEventResponseDto;
import com.mahendra.tickets.domain.dtos.CreateTicketTypeRequestDto;
import com.mahendra.tickets.domain.entities.Event;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

    CreateTicketTypeRequest fromDto(CreateTicketTypeRequestDto dto);

    CreateEventRequest fromDto(CreateEventRequestDto dto);

    CreateEventResponseDto toDto(Event event);
}
