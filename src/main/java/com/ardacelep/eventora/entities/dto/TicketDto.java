package com.ardacelep.eventora.entities.dto;


import com.ardacelep.eventora.enums.TicketStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


import java.math.BigDecimal;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class TicketDto {

    private UUID id;

    private EventDto eventDto;

    private String seatNumber;

    private BigDecimal price;

    private TicketStatus status;

    private ReservationDto reservationDto;
}
