package com.ardacelep.eventora.business.abstracts;

import com.ardacelep.eventora.entities.dto.ReservationDto;
import com.ardacelep.eventora.entities.dto.ReservationDtoIU;

import java.util.List;
import java.util.UUID;

public interface ReservationService {

    ReservationDto makeReservation(ReservationDtoIU reservationDtoIU);

    ReservationDto cancelReservation(UUID reservationId);

    List<ReservationDto> findReservationsByCustomerNameIgnoreCase(String customerName);

    ReservationDto activateReservation(UUID reservationId);

    ReservationDto findReservationById(UUID id);

    List<ReservationDto> findAllReservations();

}
