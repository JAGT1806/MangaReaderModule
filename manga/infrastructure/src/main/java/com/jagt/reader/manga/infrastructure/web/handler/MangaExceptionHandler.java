package com.jagt.reader.manga.infrastructure.web.handler;

import com.jagt.reader.shared.common.infrastructure.input.web.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

import static com.jagt.reader.shared.common.infrastructure.input.web.util.ErrorResponseBuilder.build;

@RestControllerAdvice
@RequiredArgsConstructor
public class MangaExceptionHandler {

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResponse> handleIOException(IOException exception) {
        return build(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Error en los Pojos",
                exception.getMessage()
        );
    }
}
