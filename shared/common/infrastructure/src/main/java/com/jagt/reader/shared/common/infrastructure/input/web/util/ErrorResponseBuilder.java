package com.jagt.reader.shared.common.infrastructure.input.web.util;

import com.jagt.reader.shared.common.infrastructure.input.web.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponseBuilder {
    private ErrorResponseBuilder() {}

    public static ResponseEntity<ErrorResponse> build(HttpStatus status, String error, String message) {
        return build(status, error, message != null ? List.of(message) : List.of());
    }

    public static ResponseEntity<ErrorResponse> build(HttpStatus status, String error, List<String> messages) {
        ErrorResponse errorResponse = new ErrorResponse(
                String.valueOf(status.value()),
                error,
                messages,
                LocalDateTime.now()
        );

        return ResponseEntity.status(status).body(errorResponse);
    }

}
