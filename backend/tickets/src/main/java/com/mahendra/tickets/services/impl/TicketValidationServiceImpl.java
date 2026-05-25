package com.mahendra.tickets.services.impl;

import com.mahendra.tickets.domain.entities.*;
import com.mahendra.tickets.exceptions.QrCodeNotFoundException;
import com.mahendra.tickets.exceptions.TicketNotFoundException;
import com.mahendra.tickets.repositories.QrCodeRepository;
import com.mahendra.tickets.repositories.TicketRepository;
import com.mahendra.tickets.repositories.TicketValidationRepository;
import com.mahendra.tickets.services.TicketValidationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketValidationServiceImpl implements TicketValidationService {

    private final QrCodeRepository qrCodeRepository;
    private final TicketValidationRepository ticketValidationRepository;
    private final TicketRepository ticketRepository;

    @Override
    public TicketValidation validateTicketByQrCode(UUID qrCodeId) {
        QrCode qrCode = qrCodeRepository.findByIdAndStatus(qrCodeId, QrCodeStatusEnum.ACTIVE)
                .orElseThrow(() -> new QrCodeNotFoundException((
                        String.format(
                                "Qr Code with Id %s was not found", qrCodeId
                        )
                        )));

        Ticket ticket = qrCode.getTicket();

        return validateTicket(ticket, TicketValidationMethodEnum.QR_SCAN);
    }

    private TicketValidation validateTicket(Ticket ticket, TicketValidationMethodEnum ticketValidationMethodEnum){
        TicketValidation ticketValidation = new TicketValidation();
        ticketValidation.setTicket(ticket);
        ticketValidation.setValidationMethodEnum(ticketValidationMethodEnum);

        TicketValidationStatusEnum ticketValidationStatusEnum = ticket.getValidations().stream()
                .filter(v->TicketValidationStatusEnum.VALID.equals(v.getStatus()))
                .findFirst()
                .map(v->TicketValidationStatusEnum.INVALID)
                .orElse(TicketValidationStatusEnum.VALID);

        ticketValidation.setStatus(ticketValidationStatusEnum);

        return ticketValidationRepository.save(ticketValidation);
    }

    @Override
    public TicketValidation validateTicketManually(UUID ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(TicketNotFoundException::new);
        return validateTicket(ticket, TicketValidationMethodEnum.MANUAL);
    }
}
