package com.ardacelep.eventora.exception;

import com.ardacelep.eventora.entities.enums.ErrorMessageType;
import com.ardacelep.eventora.core.helpers.ExceptionHelpers;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.Map;



@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ExceptionHelpers exHelp;


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError<Map<String, List<String>>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest webRequest) {

        return exHelp.prepareFieldValidationExceptionResponse(ex, webRequest, ErrorMessageType.VALIDATION_FAILED);

    }

    @ExceptionHandler(value = RuntimeBaseException.class)
    public ResponseEntity<ApiError> handleRuntimeBaseException(RuntimeBaseException exception, WebRequest webRequest){
        return exHelp.prepareRunTimeBaseExceptionResponse(exception, webRequest);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception, WebRequest webRequest){
        return exHelp.prepareHttpMessageNotReadableExceptionResponse(exception,webRequest);
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception, WebRequest webRequest){
        return exHelp.prepareMethodArgumentTypeMismatchExceptionResponse(exception,webRequest);
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseEntity<ApiError<Map<String, List<String>>>> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException ex, WebRequest webRequest) {

        return exHelp.prepareMissingParameterExceptionResponse(ex, webRequest, ErrorMessageType.MISSING_REQUEST_PARAM);
    }

    @ExceptionHandler(value = MissingPathVariableException.class)
    public ResponseEntity<ApiError<Map<String, List<String>>>> handleMissingPathVariableException(
            MissingPathVariableException ex, WebRequest webRequest) {

        return exHelp.prepareMissingPathVariableExceptionResponse(ex, webRequest, ErrorMessageType.MISSING_PATH_VARIABLE);
    }

    @ExceptionHandler(value = NoResourceFoundException.class)
    public ResponseEntity<ApiError<String>> handleNoResourceFoundException(
            NoResourceFoundException ex, WebRequest webRequest) {

        return exHelp.prepareNoResourceFoundExceptionResponse(ex, webRequest, ErrorMessageType.RESOURCE_NOT_FOUND);
    }






}