package com.mahendra.tickets.mappers;

import com.mahendra.tickets.domain.dtos.ListTicketResponseDto;
import com.mahendra.tickets.domain.dtos.ListTicketTicketTypeResponseDto;
import com.mahendra.tickets.domain.entities.Ticket;
import com.mahendra.tickets.domain.entities.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketMapper {

    ListTicketTicketTypeResponseDto toListTicketTicketTypeResponseDto(TicketType ticketType);

    ListTicketResponseDto toListTicketResponseDto(Ticket ticket);
}
