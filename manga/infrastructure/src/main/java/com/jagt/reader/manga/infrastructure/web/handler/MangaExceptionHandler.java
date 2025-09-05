package com.jagt.reader.manga.infrastructure.web.handler;

import com.jagt.reader.shared.common.infrastructure.input.web.response.ErrorResponse;
import com.jagt.reader.shared.i18n.domain.service.MessageProvider;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static com.jagt.reader.shared.common.infrastructure.input.web.util.ErrorResponseBuilder.build;

@RestControllerAdvice
@RequiredArgsConstructor
public class MangaExceptionHandler {
    private final MessageProvider messageProvider;

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResponse> handleFeignException(FeignException exception) {
        return build(
                HttpStatus.BAD_GATEWAY,
                messageProvider.getMessage("feign.error"),
                List.of(
                        messageProvider.getMessage("feign.error.manga.dex"),
                        exception.getMessage()
                )
        );
    }
}
