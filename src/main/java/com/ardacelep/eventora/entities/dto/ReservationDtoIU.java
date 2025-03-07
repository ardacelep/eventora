package com.ardacelep.eventora.entities.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDtoIU {

    @NotNull(message = "Reservation must be for a ticket. ticketId can not be empty.")
    private UUID ticketId;

    @NotBlank(message = "Reservation must be in the name of a customer. customerName can not be empty.")
    private String customerName;

}
