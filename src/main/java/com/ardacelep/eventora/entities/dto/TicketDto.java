package com.ardacelep.eventora.entities.dto;


import com.ardacelep.eventora.entities.enums.TicketStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;


import java.math.BigDecimal;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketDto {

    private UUID id;

    private EventDto eventDto;

    private String seatNumber;

    private BigDecimal price;

    private TicketStatus status;

    private ReservationDto reservationDto;
}
