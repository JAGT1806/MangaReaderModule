package com.jagt.reader.auth.domain.port.output;

import com.jagt.reader.auth.domain.model.enums.CodeType;

import java.time.LocalDateTime;

public interface EmailSenderPort {
    void sendCode(String to, String code, CodeType codeType, LocalDateTime expiration);
}
