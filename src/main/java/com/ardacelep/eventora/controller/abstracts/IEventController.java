package com.ardacelep.eventora.controller.abstracts;

import com.ardacelep.eventora.entities.dto.EventDto;
import com.ardacelep.eventora.entities.dto.EventDtoIU;
import com.ardacelep.eventora.entities.dto.EventDtoUpdate;

import java.util.List;
import java.util.UUID;

public interface IEventController {

    EventDto addEvent(EventDtoIU eventDtoIU);

    EventDto findEventById(UUID id);

    List<EventDto> findAllEvents();

    EventDto updateEvent(UUID id, EventDtoUpdate eventDtoUpdate);

    EventDto deleteEvent(UUID id);

    List<EventDto> searchEvents(String name);

}
