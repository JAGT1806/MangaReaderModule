package com.jagt.reader.role.infrastructure.web.handler;

import com.jagt.reader.role.domain.exception.RoleExistException;
import com.jagt.reader.role.domain.exception.RoleInUseException;
import com.jagt.reader.role.domain.exception.RoleNotFoundException;
import com.jagt.reader.shared.common.infrastructure.input.web.response.ErrorResponse;
import com.jagt.reader.shared.i18n.domain.service.MessageProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor
public class RoleExceptionHandler {
    private final MessageProvider messageProvider;

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRoleNotFoundException(RoleNotFoundException e) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, messageProvider.getMessage("entity.not.found"), messageProvider.getMessage("role.not.found", new Object[]{e.getMessage()}));
    }

    @ExceptionHandler(RoleExistException.class)
    public ResponseEntity<ErrorResponse> handleRoleExistException(RoleExistException e) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, messageProvider.getMessage("entity.exist"), messageProvider.getMessage("role.exist", new Object[]{e.getMessage()}));
    }

    @ExceptionHandler(RoleInUseException.class)
    public ResponseEntity<ErrorResponse> handleRoleInUseException(RoleInUseException e) {
        return buildErrorResponse(HttpStatus.CONFLICT, messageProvider.getMessage("entity.already_used"), e.getMessage());
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String error, String message) {
        return buildErrorResponse(status, error, message != null ? List.of(message) : List.of());
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String error, List<String> messages) {
        ErrorResponse errorResponse = new ErrorResponse(
                String.valueOf(status.value()), error, messages, LocalDateTime.now()
        );
        return ResponseEntity.status(status).body(errorResponse);
    }

}
