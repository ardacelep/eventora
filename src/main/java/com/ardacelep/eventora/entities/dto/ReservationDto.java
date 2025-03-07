package com.ardacelep.eventora.entities.dto;

import com.ardacelep.eventora.entities.enums.ReservationStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
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
