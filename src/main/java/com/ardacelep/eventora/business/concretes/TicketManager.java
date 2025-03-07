package com.ardacelep.eventora.business.concretes;

import com.ardacelep.eventora.business.abstracts.TicketService;
import com.ardacelep.eventora.dataAccess.EventDao;
import com.ardacelep.eventora.dataAccess.TicketDao;
import com.ardacelep.eventora.entities.Event;
import com.ardacelep.eventora.entities.Ticket;
import com.ardacelep.eventora.entities.dto.TicketDto;
import com.ardacelep.eventora.entities.dto.TicketDtoIU;
import com.ardacelep.eventora.entities.enums.ErrorMessageType;
import com.ardacelep.eventora.entities.enums.TicketStatus;
import com.ardacelep.eventora.exception.RuntimeBaseException;
import com.ardacelep.eventora.core.helpers.TicketManagerHelpers;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketManager implements TicketService {

    private final TicketDao ticketDao;

    private final EventDao eventDao;

    private final TicketManagerHelpers tickManHelp;

    @Override
    @Transactional
    public TicketDto addTicket(TicketDtoIU ticketDtoIU) {

        UUID eventId = ticketDtoIU.getEventId();

        Optional<Event> ticketEventOptional = eventDao.findById(eventId);

        if (ticketEventOptional.isEmpty()) {
            throw new RuntimeBaseException(ErrorMessageType.NO_RECORD_EXISTS,MessageFormat.format("given event does not exist, searched in events for id: {0}", eventId), HttpStatus.NOT_FOUND);
        }

        Event givenEvent = ticketEventOptional.get();

        if (tickManHelp.doesTicketExist(givenEvent.getId(), ticketDtoIU.getSeatNumber())){
            throw new RuntimeBaseException(ErrorMessageType.RECORD_ALREADY_EXISTS, MessageFormat.format("ticket already exists for seat {0} in the event with the id of: {1} ",ticketDtoIU.getSeatNumber(),givenEvent.getId()), HttpStatus.CONFLICT);
        }

        Ticket ticket = new Ticket();

        ticket.setStatus(TicketStatus.AVAILABLE);

        BeanUtils.copyProperties(ticketDtoIU,ticket);

        ticket.setEvent(givenEvent);

        ticket.setStatus(TicketStatus.AVAILABLE);

        ticketDao.save(ticket);

        return tickManHelp.convertTicketToDto(ticket, givenEvent);

    }

    @Override
    @Transactional
    public TicketDto findTicketById(UUID id) {

        Optional<Ticket> optional = ticketDao.findById(id);

        if (optional.isEmpty()) throw new RuntimeBaseException(ErrorMessageType.NO_RECORD_EXISTS, MessageFormat.format("searched in tickets for id: {0}",id), HttpStatus.NOT_FOUND);

        Ticket dbTicket = optional.get();

        Event dbEvent = dbTicket.getEvent();

        return tickManHelp.convertTicketToDto(dbTicket,dbEvent);
    }

    @Override
    @Transactional
    public List<TicketDto> findAllTickets() {

        List<TicketDto> ticketDtoList = new ArrayList<>();

        for (Ticket ticket : ticketDao.findAll()) {
            TicketDto ticketDto = tickManHelp.convertTicketToDto(ticket,ticket.getEvent());

            ticketDtoList.add(ticketDto);

        }

        return ticketDtoList;
    }

    @Override
    @Transactional
    public TicketDto deleteTicketById(UUID id) {

        Optional<Ticket> optional = ticketDao.findById(id);

        if (optional.isEmpty()) throw new RuntimeBaseException(ErrorMessageType.NO_RECORD_EXISTS, MessageFormat.format("given ticket does not exist, searched in tickets for id: {0}",id), HttpStatus.NOT_FOUND);

        Ticket dbTicket = optional.get();

        // ilişki çift taraflı olduğu için ticket üzerinden eriştiğim event de db'dekini temsil eder.
        Event dbEvent = dbTicket.getEvent();

        TicketDto responseTicket = tickManHelp.convertTicketToDto(dbTicket,dbEvent);

        // tikcet'ı direkt tablodan silmek ilişkiyi ve verileri hibernate'in yönetiminden çıkardığı için
        // sorun yaratır. yöntem işe yaramaz. bu yüzden db'deki event'i temsil eden event'in tickets
        // listesinden çıkarttık, orphanRemoval = true olduğu için parent'sız kalan ticket db'den silindi.
        dbEvent.getTickets().remove(dbTicket);
        // fonksiyon @Transactional ile işaretlendiği için spring bu fonksiyondaki işlemleri takip eder,
        // eventDao.save(dbEvent) yapmamıza gerek kalmadan değişikler db'de güncellenir.

        return responseTicket;
    }

    @Override
    @Transactional
    public TicketDto findTicketByEventIdAndSeatNumber(UUID eventId, String seatNumber){

        Optional<Event> optionalEvent = eventDao.findById(eventId);

        if (optionalEvent.isEmpty()) throw new RuntimeBaseException(ErrorMessageType.NO_RECORD_EXISTS, MessageFormat.format("given event does not exist, searched in events for id: {0}",eventId), HttpStatus.NOT_FOUND);

        Optional<Ticket> optionalTicket = ticketDao.findTicketByEventIdAndSeatNumber(eventId,seatNumber);

        if (optionalTicket.isEmpty()) throw new RuntimeBaseException(ErrorMessageType.NO_RECORD_EXISTS, MessageFormat.format("given ticket does not exist, searched in tickets for event_id: {0} and seat_number: {1} ",eventId,seatNumber), HttpStatus.NOT_FOUND);

        return tickManHelp.convertTicketToDto(optionalTicket.get(),optionalEvent.get());

    }

}
