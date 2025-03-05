package com.ardacelep.eventora.entities.dto;

import com.ardacelep.eventora.enums.ReservationStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ReservationDto {

    private UUID id;

    private TicketDto ticketDto;

    private String customerName;

    private LocalDateTime reservationDate;

    private ReservationStatus status;

}
