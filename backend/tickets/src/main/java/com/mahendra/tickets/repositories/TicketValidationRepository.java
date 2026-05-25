package com.mahendra.tickets.repositories;

import com.mahendra.tickets.domain.entities.TicketValidation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface TicketValidationRepository extends JpaRepository<TicketValidation, UUID> {
}
