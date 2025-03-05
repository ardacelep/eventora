package com.ardacelep.eventora.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Exception<E> {

    private String hostName;

    private String path;

    private String requestType;

    private LocalDateTime createdAt;

    private E message;

}
