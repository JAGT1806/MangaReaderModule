package com.jagt.reader.user.domain.exception;

import com.jagt.reader.shared.common.domain.exception.ApplicationException;

public class ProfilePictureValidationException extends ApplicationException {
    public ProfilePictureValidationException(String message) {
        super(message);
    }
}
