package com.ardacelep.eventora.core.helpers;

import com.ardacelep.eventora.entities.Reservation;
import com.ardacelep.eventora.entities.Ticket;
import com.ardacelep.eventora.entities.dto.EventDto;
import com.ardacelep.eventora.entities.dto.ReservationDto;
import com.ardacelep.eventora.entities.dto.TicketDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ReservationManagerHelpers {


    public ReservationDto convertReservationtoDto(Reservation reservation){

        ReservationDto reservationDto = new ReservationDto();

        BeanUtils.copyProperties(reservation,reservationDto);

        Ticket ticket = reservation.getTicket();

        TicketDto ticketDto = new TicketDto();

        EventDto eventDto = new EventDto();

        BeanUtils.copyProperties(ticket,ticketDto);

        BeanUtils.copyProperties(ticket.getEvent(),eventDto);

        ticketDto.setEventDto(eventDto);

        reservationDto.setTicketDto(ticketDto);

        return reservationDto;

    }

}
