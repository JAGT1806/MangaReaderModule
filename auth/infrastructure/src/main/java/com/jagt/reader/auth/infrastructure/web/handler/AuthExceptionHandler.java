package com.jagt.reader.auth.infrastructure.web.handler;

import com.jagt.reader.auth.domain.exception.InvalidCodeException;
import com.jagt.reader.auth.domain.exception.SendEmailException;
import com.jagt.reader.shared.common.infrastructure.input.web.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static com.jagt.reader.shared.common.infrastructure.input.web.util.ErrorResponseBuilder.build;

@RestControllerAdvice
@RequiredArgsConstructor
public class AuthExceptionHandler {

    @ExceptionHandler(InvalidCodeException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCodeException(InvalidCodeException e) {
        return build(
                HttpStatus.BAD_REQUEST,
                e.getMessage(),
                List.of()
        );
    }

    @ExceptionHandler(SendEmailException.class)
    public ResponseEntity<ErrorResponse> handleSendEmailException(SendEmailException e) {
        return build(
                HttpStatus.SERVICE_UNAVAILABLE,
                e.getMessage(),
                List.of()
        );
    }
}
