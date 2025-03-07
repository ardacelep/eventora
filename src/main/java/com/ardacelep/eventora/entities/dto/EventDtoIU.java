package com.ardacelep.eventora.entities.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventDtoIU {

    @NotBlank(message = "name can not be empty.")
    @Size(min = 3, max = 50, message = "name must be between 3 and 50 characters")
    private String name;

    @Future(message = "date must be in the future.")
    @NotNull
    private LocalDateTime date;

    @NotBlank(message = "venue can not be empty.")
    @Size(min = 3, message = "venue should be at least 3 characters long.")
    private String venue;

}
