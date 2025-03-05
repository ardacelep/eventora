package com.ardacelep.eventora.helpers;

import com.ardacelep.eventora.dataAccess.EventDao;
import com.ardacelep.eventora.entities.Event;
import com.ardacelep.eventora.entities.Ticket;
import com.ardacelep.eventora.entities.dto.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EventManagerHelpers {

    private final EventDao eventDao;

    public EventManagerHelpers(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    public EventDto convertEventToDto(Event dbEvent) {

        EventDto responseEvent = new EventDto();

        BeanUtils.copyProperties(dbEvent, responseEvent);

        if (dbEvent.getTickets() != null && !dbEvent.getTickets().isEmpty()) {

            for (Ticket ticket : dbEvent.getTickets()) {

                TicketDto ticketDto = new TicketDto();
                BeanUtils.copyProperties(ticket, ticketDto);

                if (ticket.getReservation() != null) {
                    ReservationDto reservationDto = new ReservationDto();
                    BeanUtils.copyProperties(ticket.getReservation(),reservationDto);
                    ticketDto.setReservationDto(reservationDto);
                }
                responseEvent.getTickets().add(ticketDto);
            }
        }
        return responseEvent;
    }

    public boolean doesEventExist(EventDtoIU eventDtoIU){

        Optional<Event> optional = eventDao.findByNameIgnoreCaseAndVenueIgnoreCaseAndDate(eventDtoIU.getName(),eventDtoIU.getVenue(),eventDtoIU.getDate());

        return optional.isPresent();

    }

}
