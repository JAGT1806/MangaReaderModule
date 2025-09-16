package com.jagt.reader.auth.domain.port.output;

import java.time.LocalDateTime;

public interface EmailSenderPort {
    void sendCode(String to, String code, LocalDateTime expiration);
}
