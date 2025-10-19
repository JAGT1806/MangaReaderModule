package com.jagt.reader.shared.security.domain.exception;

public class SelfRoleModificationException extends RuntimeException {
    public SelfRoleModificationException(String message) {
        super(message);
    }
}
