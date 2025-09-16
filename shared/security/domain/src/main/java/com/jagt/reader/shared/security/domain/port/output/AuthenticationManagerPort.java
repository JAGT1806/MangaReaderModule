package com.jagt.reader.shared.security.domain.port.output;

public interface AuthenticationManagerPort {
    void authenticate(String username, String password);
}
