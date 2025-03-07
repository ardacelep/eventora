package com.ardacelep.eventora.entities.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventDtoUpdate {

    @Size(min = 3, max = 50, message = "name must be between 3 and 50 characters")
    private String name;

    @Future(message = "date must be in the future.")
    private LocalDateTime date;

    @Size(min = 3, message = "venue should be at least 3 characters long.")
    private String venue;

}
