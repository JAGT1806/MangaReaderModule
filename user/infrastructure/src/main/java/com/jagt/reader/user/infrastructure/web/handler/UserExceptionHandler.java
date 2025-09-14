package com.jagt.reader.user.infrastructure.web.handler;

import com.jagt.reader.shared.common.infrastructure.input.web.response.ErrorResponse;
import com.jagt.reader.shared.i18n.domain.service.MessageProvider;
import com.jagt.reader.user.domain.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.io.IOException;

import static com.jagt.reader.shared.common.infrastructure.input.web.util.ErrorResponseBuilder.build;

@RestControllerAdvice
@RequiredArgsConstructor
public class UserExceptionHandler {
    private final MessageProvider messageProvider;

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException e) {
        return build(
                HttpStatus.NOT_FOUND,
                messageProvider.getMessage("entity.not.found"),
                messageProvider.getMessage("user.not.found", new Object[]{e.getMessage()})
        );
    }

    @ExceptionHandler(UserEmailNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserEmailNotFoundException(UserEmailNotFoundException e) {
        return build(
                HttpStatus.NOT_FOUND,
                messageProvider.getMessage("entity.not.found"),
                messageProvider.getMessage("user.email.not.found", new Object[]{e.getMessage()})
        );
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        return build(HttpStatus.PAYLOAD_TOO_LARGE, messageProvider.getMessage("file.image.upload.error"), messageProvider.getMessage("file.image.size.exceeded"));
    }

    @ExceptionHandler(ProfilePictureValidationException.class)
    public ResponseEntity<ErrorResponse> handleProfilePictureValidationException(ProfilePictureValidationException e) {
        return build(HttpStatus.BAD_REQUEST, messageProvider.getMessage("file.image.upload.error"), messageProvider.getMessage(e.getMessage()));
    }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<ErrorResponse> handleFileStorageException(FileStorageException e) {
        return build(
                HttpStatus.INTERNAL_SERVER_ERROR,
                messageProvider.getMessage("file.storage.error"),
                messageProvider.getMessage(e.getMessage())
        );
    }

    @ExceptionHandler(ProfilePictureNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProfilePictureNotFoundException(ProfilePictureNotFoundException e) {
        return build(
                HttpStatus.NOT_FOUND,
                messageProvider.getMessage("profile.picture.error"),
                messageProvider.getMessage(e.getMessage())
        );
    }

    @ExceptionHandler(ProfilePictureAlreadyDefaultException.class)
    public ResponseEntity<ErrorResponse> handleProfilePictureAlreadyDefaultException(ProfilePictureAlreadyDefaultException e) {
        return build(
                HttpStatus.CONFLICT,
                messageProvider.getMessage("profile.picture.error"),
                messageProvider.getMessage(e.getMessage())
        );
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResponse> handleIOException(IOException e) {
        return build(
                HttpStatus.INTERNAL_SERVER_ERROR,
                messageProvider.getMessage("profile.picture.error"),
                messageProvider.getMessage("profile.picture.io.error") + ": " + e.getMessage()
        );
    }
}
