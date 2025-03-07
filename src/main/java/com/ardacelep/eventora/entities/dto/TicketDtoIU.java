package com.ardacelep.eventora.entities.dto;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketDtoIU {

    @NotNull(message = "Ticket must be for an event. eventId can not be empty.")
    private UUID eventId;

    @NotNull(message = "seatNumber can not be empty.")
    private String seatNumber;

    @NotNull(message = "price can not be empty")
    @DecimalMin(value = "0.00", inclusive = true, message = "price must be equal to or higher than 0.00 .")
    private BigDecimal price;


}
