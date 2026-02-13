package com.bernardo_duarte.spring_api.exception;

import com.bernardo_duarte.spring_api.dto.SummaryResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<SummaryResponse> handleAiApiError(RestClientException e) {
        SummaryResponse errorBody = new SummaryResponse(false, "Erro ao se comunicar com o serviço de IA");

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorBody);
    }
}
