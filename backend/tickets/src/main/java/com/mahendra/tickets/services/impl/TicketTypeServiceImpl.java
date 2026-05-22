package com.mahendra.tickets.services.impl;

import com.mahendra.tickets.domain.entities.Ticket;
import com.mahendra.tickets.domain.entities.TicketStatusEnum;
import com.mahendra.tickets.domain.entities.TicketType;
import com.mahendra.tickets.domain.entities.User;
import com.mahendra.tickets.exceptions.TicketSoldOutException;
import com.mahendra.tickets.exceptions.TicketTypeNotFoundException;
import com.mahendra.tickets.repositories.TicketRepository;
import com.mahendra.tickets.repositories.TicketTypeRepository;
import com.mahendra.tickets.repositories.UserRepository;
import com.mahendra.tickets.services.QrCodeService;
import com.mahendra.tickets.services.TicketTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {

    private final UserRepository userRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final TicketRepository ticketRepository;
    private final QrCodeService qrCodeService;
    @Override
    @Transactional
    public Ticket purchaseTicket(UUID userId, UUID ticketTypeId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User with ID %s was not found", userId)));

        TicketType ticketType = ticketTypeRepository.findByIdWithLock(ticketTypeId).orElseThrow(() -> new TicketTypeNotFoundException(
                String.format("Ticket Type with ID %s was not found", ticketTypeId)
        ));

        int purchasedTicket = ticketRepository.countByTicketTypeId(ticketType.getId());
        Integer totalAvailable = ticketType.getTotalAvailable();

        if(purchasedTicket + 1 > totalAvailable){
            throw new TicketSoldOutException();
        }

        Ticket ticket = new Ticket();
        ticket.setStatus(TicketStatusEnum.PURCHASED);
        ticket.setTicketType(ticketType);
        ticket.setPurchaser(user);

        Ticket savedTicket = ticketRepository.save(ticket);
        qrCodeService.generateQrCode(savedTicket);

        return ticketRepository.save(savedTicket);
    }
}
