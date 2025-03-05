package com.ardacelep.eventora.exception;

import com.ardacelep.eventora.enums.ErrorMessageType;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RuntimeBaseException extends RuntimeException {

    private final HttpStatus httpStatus;

    private final ErrorMessageType errorMessageType;

    public RuntimeBaseException(ErrorMessageType errorMessageType, String message, HttpStatus httpStatus) {
        super(message);
        this.errorMessageType = errorMessageType;
        this.httpStatus = httpStatus;

    }
}
