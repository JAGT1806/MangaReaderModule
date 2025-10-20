package com.jagt.reader.bootstrap.handler;

import com.jagt.reader.shared.common.infrastructure.input.web.response.ErrorResponse;
import com.jagt.reader.shared.i18n.domain.service.MessageProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.List;

import static com.jagt.reader.shared.common.infrastructure.input.web.util.ErrorResponseBuilder.build;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final MessageProvider messageProvider;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        return build(HttpStatus.BAD_REQUEST, messageProvider.getMessage("data.bad.request"), errors);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        return build(HttpStatus.BAD_REQUEST, messageProvider.getMessage("data.bad.request"), e.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResponse> handleIOException(IOException e) {
        return build(
                HttpStatus.INTERNAL_SERVER_ERROR,
                messageProvider.getMessage("file.process.error"),
                messageProvider.getMessage("file.general.error")
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        return build(
                HttpStatus.INTERNAL_SERVER_ERROR,
                messageProvider.getMessage("server.error"),
                messageProvider.getMessage("server.error.unexpected")
        );
    }
}
