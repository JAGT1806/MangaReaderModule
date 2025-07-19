package com.jagt.reader.role.domain.exception;

import com.jagt.reader.shared.common.domain.exception.ApplicationException;

public class RoleExistException extends ApplicationException {
    public RoleExistException(String message) {
        super(message);
    }
}
