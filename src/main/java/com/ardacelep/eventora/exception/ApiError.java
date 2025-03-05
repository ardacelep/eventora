package com.ardacelep.eventora.exception;

import com.ardacelep.eventora.enums.ErrorMessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiError<E> {

    private Integer status;

    private ErrorMessageType errorMessageType;

    private Exception<E> exception;

}
