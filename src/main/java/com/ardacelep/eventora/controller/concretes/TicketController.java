package com.ardacelep.eventora.controller.concretes;


import com.ardacelep.eventora.business.abstracts.TicketService;
import com.ardacelep.eventora.controller.abstracts.ITicketController;
import com.ardacelep.eventora.entities.dto.TicketDto;
import com.ardacelep.eventora.entities.dto.TicketDtoIU;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest/api/ticket")
@RequiredArgsConstructor
public class TicketController implements ITicketController {

    private final TicketService ticketService;

    @Override
    @PostMapping("/add")
    public TicketDto addTicket(@RequestBody @Valid TicketDtoIU ticketDtoIU) {
        return ticketService.addTicket(ticketDtoIU);
    }

    @Override
    @GetMapping("find/{id}")
    public TicketDto findTicketById(@PathVariable UUID id) {
        return ticketService.findTicketById(id);
    }

    @Override
    @GetMapping("/list")
    public List<TicketDto> findAllTickets() {
        return ticketService.findAllTickets();
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public TicketDto deleteTicketById(@PathVariable UUID id) {
        return ticketService.deleteTicketById(id);
    }

    @Override
    @GetMapping("/search")
    public TicketDto findTicketByEventIdAndSeatNumber(@RequestParam UUID eventId,@RequestParam String seatNumber) {
        return ticketService.findTicketByEventIdAndSeatNumber(eventId,seatNumber);
    }
}
