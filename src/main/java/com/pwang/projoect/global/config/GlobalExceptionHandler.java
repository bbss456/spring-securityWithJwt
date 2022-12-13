package com.pwang.projoect.global.config;

import com.pwang.projoect.global.exception.BusinessException;
import com.pwang.projoect.global.exception.ErrorCode;
import com.pwang.projoect.global.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.QueryException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException error) {
        log.warn("handleEntityNotFoundException", error);
        final ErrorCode errorCode = error.getErrorCode();
        final ErrorResponse response = ErrorResponse.of(errorCode);

        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
    }
}