package com.jagt.reader.user.domain.exception;

import com.jagt.reader.shared.common.domain.exception.ApplicationException;

public class FileStorageException extends ApplicationException {
    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
