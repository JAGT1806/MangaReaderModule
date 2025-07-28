package com.jagt.reader.user.domain.exception;

import com.jagt.reader.shared.common.domain.exception.ApplicationException;

public class ProfilePictureNotFoundException extends ApplicationException {
    public ProfilePictureNotFoundException(String message) {
        super(message);
    }
}
