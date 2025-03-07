package com.ardacelep.eventora.controller.concretes;

import com.ardacelep.eventora.business.abstracts.EventService;
import com.ardacelep.eventora.controller.abstracts.IEventController;
import com.ardacelep.eventora.entities.dto.EventDto;
import com.ardacelep.eventora.entities.dto.EventDtoIU;
import com.ardacelep.eventora.entities.dto.EventDtoUpdate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest/api/event")
@RequiredArgsConstructor
public class EventController implements IEventController {

    private final EventService eventService;

    @Override
    @PostMapping("/add")
    public EventDto addEvent(@RequestBody @Valid EventDtoIU eventDtoIU) {
        return eventService.addEvent(eventDtoIU);
    }

    @Override
    @GetMapping("/find/{id}")
    public EventDto findEventById(@PathVariable UUID id) {
        return eventService.findEventById(id);
    }

    @Override
    @GetMapping("/list")
    public List<EventDto> findAllEvents() {
        return eventService.findAllEvents();
    }

    @Override
    @PatchMapping("/update/{id}")
    public EventDto updateEvent(@PathVariable UUID id, @RequestBody @Valid EventDtoUpdate eventDtoUpdate) {
        return eventService.updateEvent(id, eventDtoUpdate);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public EventDto deleteEvent(@PathVariable UUID id) {
        return eventService.deleteEvent(id);
    }

    @Override
    @GetMapping("/search")
    public List<EventDto> searchEvents(@RequestParam String name) {
        return eventService.searchEvents(name);
    }
}
