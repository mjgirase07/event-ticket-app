package com.mahendra.tickets.services;

import com.mahendra.tickets.domain.entities.QrCode;
import com.mahendra.tickets.domain.entities.Ticket;

import java.util.UUID;

public interface QrCodeService {
    QrCode generateQrCode(Ticket ticket);

    byte[] getQrCodeImageForUserAndTicket(UUID userId, UUID ticketId);
}
