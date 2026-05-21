package com.mahendra.tickets.services;

import com.mahendra.tickets.domain.entities.QrCode;
import com.mahendra.tickets.domain.entities.Ticket;

public interface QrCodeService {
    QrCode generateQrCode(Ticket ticket);
}
