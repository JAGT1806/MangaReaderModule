package com.jagt.reader.shared.security.domain.port.output;

import java.util.Map;

public interface TokenProviderPort {
    String generateToken(String username, Map<String, Object> claims);
    boolean validateToken(String token);
    String extractUsername(String token);
}
