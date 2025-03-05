package com.ardacelep.eventora.entities.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EventDto {

    private UUID id;

    private String name;

    private LocalDateTime date;

    private String venue;

    private List<TicketDto> tickets = new ArrayList<>();

}
