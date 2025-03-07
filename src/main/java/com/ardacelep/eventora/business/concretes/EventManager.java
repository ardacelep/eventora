package com.ardacelep.eventora.business.concretes;

import com.ardacelep.eventora.business.abstracts.EventService;
import com.ardacelep.eventora.core.ReflectionUtils;
import com.ardacelep.eventora.dataAccess.EventDao;
import com.ardacelep.eventora.entities.Event;

import com.ardacelep.eventora.entities.dto.EventDto;
import com.ardacelep.eventora.entities.dto.EventDtoIU;

import com.ardacelep.eventora.entities.dto.EventDtoUpdate;
import com.ardacelep.eventora.entities.enums.ErrorMessageType;
import com.ardacelep.eventora.exception.RuntimeBaseException;
import com.ardacelep.eventora.core.helpers.EventManagerHelpers;
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
public class EventManager implements EventService {

    private final EventDao eventDao;
    private final EventManagerHelpers evManHelp;

    // sınıfın tek constructor'ı varsa spring @Autowired koymaya gerek olmadan kendisi consturctor
    // injection ile constructor içindeki component'ları enjekte eder.
    // elle yazmak yerine lombok'un 'final' ifadesine sahip değişkenler için constructor oluşturduğu
    // @RequiredArgsConstructor anotasyonu da vardır.
    public EventManager(EventDao eventDao, EventManagerHelpers evManHelp){
        this.eventDao = eventDao;
        this.evManHelp = evManHelp;
    }

    @Override
    @Transactional
    public EventDto addEvent(EventDtoIU eventDtoIU) {

        if (evManHelp.doesEventExist(eventDtoIU)) throw new RuntimeBaseException(ErrorMessageType.RECORD_ALREADY_EXISTS,MessageFormat.format("event with the same name, venue and date already exists || name: {0} | venue: {1} | date: {2}",eventDtoIU.getName(),eventDtoIU.getVenue(),eventDtoIU.getDate()), HttpStatus.CONFLICT);

        Event newEvent = new Event();

        BeanUtils.copyProperties(eventDtoIU, newEvent);

        eventDao.save(newEvent);

        return evManHelp.convertEventToDto(newEvent);

    }

    @Override
    @Transactional
    public EventDto findEventById(UUID id) {

        Optional<Event> optional = eventDao.findById(id);

        if (optional.isEmpty()) {
            throw new RuntimeBaseException(ErrorMessageType.NO_RECORD_EXISTS,MessageFormat.format("searched in events for id: {0}",id), HttpStatus.NOT_FOUND);
        }

        return evManHelp.convertEventToDto(optional.get());
    }

    @Override
    @Transactional
    public List<EventDto> findAllEvents() {

        List<EventDto> eventDtoList = new ArrayList<>();

        for (Event event : eventDao.findAll()) {
            eventDtoList.add(evManHelp.convertEventToDto(event));
        }

        return eventDtoList;
    }

    @Override
    @Transactional
    public EventDto updateEvent(UUID id, EventDtoUpdate updatedEvent){

        Optional<Event> optional = eventDao.findById(id);

        if (optional.isEmpty()) {
            throw new RuntimeBaseException(ErrorMessageType.NO_RECORD_EXISTS,MessageFormat.format("searched in events for id: {0}",id), HttpStatus.NOT_FOUND);
        }

        Event dbEvent = optional.get();

        ReflectionUtils.copyNonNullProperties(updatedEvent, dbEvent);

        eventDao.save(dbEvent);

        return evManHelp.convertEventToDto(dbEvent);

    }

    @Override
    @Transactional
    public EventDto deleteEvent(UUID id) {

        Optional<Event> optional = eventDao.findById(id);

        if (optional.isEmpty()){
            throw new RuntimeBaseException(ErrorMessageType.NO_RECORD_EXISTS, MessageFormat.format("searched in events for id: {0}",id), HttpStatus.NOT_FOUND);
        }

        Event dbEvent = optional.get();

        EventDto responseEvent = evManHelp.convertEventToDto(dbEvent);

        eventDao.deleteById(dbEvent.getId());

        return responseEvent;
    }

    @Override
    @Transactional
    public List<EventDto> searchEvents(String name) {

        List<EventDto> eventDtoList = new ArrayList<>();

        List<Event> foundEvents = eventDao.findByNameLike(name);

        if (!foundEvents.isEmpty()){
            for (Event foundEvent : foundEvents) {

                EventDto foundEventDto = evManHelp.convertEventToDto(foundEvent);
                eventDtoList.add(foundEventDto);

            }
        }

        return eventDtoList;
    }

}


