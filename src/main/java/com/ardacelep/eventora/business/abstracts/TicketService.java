package com.ardacelep.eventora.business.abstracts;

import com.ardacelep.eventora.entities.dto.TicketDto;
import com.ardacelep.eventora.entities.dto.TicketDtoIU;

import java.util.List;
import java.util.UUID;

public interface TicketService {

    TicketDto addTicket(TicketDtoIU ticketDtoIU);

    TicketDto findTicketById(UUID id);

    List<TicketDto> findAllTickets();

    TicketDto deleteTicketById(UUID id);

    TicketDto findTicketByEventIdAndSeatNumber(UUID eventId, String seatNumber);
}


