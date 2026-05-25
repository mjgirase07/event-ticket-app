package com.mahendra.tickets.domain.dtos;

import com.mahendra.tickets.domain.entities.TicketValidationMethodEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketValidationRequestDto {
    private UUID id;
    private TicketValidationMethodEnum method;
}
