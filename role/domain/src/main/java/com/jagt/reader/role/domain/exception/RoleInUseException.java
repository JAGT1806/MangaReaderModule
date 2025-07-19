package com.jagt.reader.role.domain.exception;

import com.jagt.reader.shared.common.domain.exception.ApplicationException;

public class RoleInUseException extends ApplicationException {
    public RoleInUseException(String message) {
        super(message);
    }
}
