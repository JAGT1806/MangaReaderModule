package com.jagt.reader.role.domain.exception;

import com.jagt.reader.shared.common.domain.exception.ApplicationException;

public class RoleNotFoundException extends ApplicationException {
    public RoleNotFoundException(String message) {
        super(message);
    }
}
