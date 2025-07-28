package com.jagt.reader.user.domain.exception;

import com.jagt.reader.shared.common.domain.exception.ApplicationException;

public class UserEmailNotFoundException extends ApplicationException {
    public UserEmailNotFoundException(String message) {
        super(message);
    }
}
