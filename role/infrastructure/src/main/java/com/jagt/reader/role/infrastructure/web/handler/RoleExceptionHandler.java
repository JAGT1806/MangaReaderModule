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

import static com.jagt.reader.shared.common.infrastructure.input.web.util.ErrorResponseBuilder.build;

@RestControllerAdvice
@RequiredArgsConstructor
public class RoleExceptionHandler {
    private final MessageProvider messageProvider;

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRoleNotFoundException(RoleNotFoundException e) {
        return build(HttpStatus.NOT_FOUND, messageProvider.getMessage("entity.not.found"), messageProvider.getMessage("role.not.found", new Object[]{e.getMessage()}));
    }

    @ExceptionHandler(RoleExistException.class)
    public ResponseEntity<ErrorResponse> handleRoleExistException(RoleExistException e) {
        return build(HttpStatus.BAD_REQUEST, messageProvider.getMessage("entity.exist"), messageProvider.getMessage("role.exist", new Object[]{e.getMessage()}));
    }

    @ExceptionHandler(RoleInUseException.class)
    public ResponseEntity<ErrorResponse> handleRoleInUseException(RoleInUseException e) {
        return build(HttpStatus.CONFLICT, messageProvider.getMessage("entity.already_used"), e.getMessage());
    }

}
