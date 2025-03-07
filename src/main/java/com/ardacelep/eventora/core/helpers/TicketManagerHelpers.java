package com.ardacelep.eventora.core.helpers;

import com.ardacelep.eventora.dataAccess.TicketDao;
import com.ardacelep.eventora.entities.Event;
import com.ardacelep.eventora.entities.Ticket;
import com.ardacelep.eventora.entities.dto.EventDto;
import com.ardacelep.eventora.entities.dto.ReservationDto;
import com.ardacelep.eventora.entities.dto.TicketDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Data
@Component
@RequiredArgsConstructor
public class TicketManagerHelpers {

    private final TicketDao ticketDao;

    public TicketDto convertTicketToDto(Ticket ticket, Event dbEvent){

        TicketDto ticketDto = new TicketDto();

        BeanUtils.copyProperties(ticket,ticketDto);

        EventDto responseEvent = new EventDto();

        BeanUtils.copyProperties(dbEvent, responseEvent);

        ticketDto.setEventDto(responseEvent);

        if (ticket.getReservation() != null) {
            ReservationDto reservationDto = new ReservationDto();
            BeanUtils.copyProperties(ticket.getReservation(),reservationDto);
            ticketDto.setReservationDto(reservationDto);

        }

        return ticketDto;

    }

    public boolean doesTicketExist(UUID eventId,String seatNumber){

        Optional<Ticket> optional = ticketDao.findTicketByEventIdAndSeatNumber(eventId, seatNumber);

        return optional.isPresent();

    }



}
