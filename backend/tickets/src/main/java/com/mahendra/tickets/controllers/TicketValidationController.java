package com.mahendra.tickets.controllers;

import com.mahendra.tickets.domain.dtos.TicketValidationRequestDto;
import com.mahendra.tickets.domain.dtos.TicketValidationResponseDto;
import com.mahendra.tickets.domain.entities.TicketValidation;
import com.mahendra.tickets.domain.entities.TicketValidationMethodEnum;
import com.mahendra.tickets.mappers.TicketValidationMapper;
import com.mahendra.tickets.services.TicketValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/ticket-validations")
@RequiredArgsConstructor
public class TicketValidationController {

    private final TicketValidationService ticketValidationService;
    private final TicketValidationMapper ticketValidationMapper;

    @PostMapping
    public ResponseEntity<TicketValidationResponseDto> validateTicket(
            @RequestBody TicketValidationRequestDto ticketValidationRequestDto
            ){
        TicketValidationMethodEnum method = ticketValidationRequestDto.getMethod();
        TicketValidation ticketValidation;

        if (TicketValidationMethodEnum.MANUAL.equals(method)) {
            ticketValidation = ticketValidationService.validateTicketManually(ticketValidationRequestDto.getId());
        } else {
            ticketValidation = ticketValidationService.validateTicketByQrCode(ticketValidationRequestDto.getId());
        }

        return ResponseEntity.ok(
                ticketValidationMapper.toTicketValidationResponseDto(ticketValidation)
        );
    }
}
