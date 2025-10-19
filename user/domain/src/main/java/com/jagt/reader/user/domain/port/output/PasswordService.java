package com.jagt.reader.user.domain.port.output;

public interface PasswordService {
    String encode(String rawPassword);
    boolean matches(String rawPassword, String encodedPassword);
}
