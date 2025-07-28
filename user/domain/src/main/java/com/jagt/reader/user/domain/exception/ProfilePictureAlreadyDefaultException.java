package com.jagt.reader.user.domain.exception;

import com.jagt.reader.shared.common.domain.exception.ApplicationException;

public class ProfilePictureAlreadyDefaultException extends ApplicationException {
    public ProfilePictureAlreadyDefaultException(String message) {
        super(message);
    }
}
