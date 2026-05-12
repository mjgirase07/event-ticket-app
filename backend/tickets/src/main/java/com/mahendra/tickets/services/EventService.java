package com.mahendra.tickets.services;

import com.mahendra.tickets.domain.CreateEventRequest;
import com.mahendra.tickets.domain.entities.Event;

import java.util.UUID;

public interface EventService {
    Event createEvent(UUID organizerId, CreateEventRequest event);
}
