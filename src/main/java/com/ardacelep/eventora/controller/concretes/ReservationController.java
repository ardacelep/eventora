package com.ardacelep.eventora.controller.concretes;


import com.ardacelep.eventora.business.abstracts.ReservationService;
import com.ardacelep.eventora.controller.abstracts.IReservationController;
import com.ardacelep.eventora.entities.dto.ReservationDto;
import com.ardacelep.eventora.entities.dto.ReservationDtoIU;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest/api/reservation")
public class ReservationController implements IReservationController {

    @Autowired
    ReservationService reservationService;

    @Override
    @PostMapping("/reserve")
    public ReservationDto makeReservation(@RequestBody @Valid ReservationDtoIU reservationDtoIU) {
        return reservationService.makeReservation(reservationDtoIU);
    }

    @Override
    @PostMapping("/cancel/{reservationId}")
    public ReservationDto cancelReservation(@PathVariable UUID reservationId) {
        return reservationService.cancelReservation(reservationId);
    }

    @Override
    @GetMapping("/find/customer-name/{customerName}")
    public List<ReservationDto> findReservationsByCustomerNameIgnoreCase(@PathVariable String customerName) {
        return reservationService.findReservationsByCustomerNameIgnoreCase(customerName);
    }

    @Override
    @PostMapping("/activate/{reservationId}")
    public ReservationDto activateReservation(@PathVariable UUID reservationId) {
        return reservationService.activateReservation(reservationId);
    }

    @Override
    @GetMapping("/find/id/{id}")
    public ReservationDto findReservationById(@PathVariable UUID id) {
        return reservationService.findReservationById(id);
    }

    @Override
    @GetMapping("/list")
    public List<ReservationDto> findAllReservations() {
        return reservationService.findAllReservations();
    }
}
