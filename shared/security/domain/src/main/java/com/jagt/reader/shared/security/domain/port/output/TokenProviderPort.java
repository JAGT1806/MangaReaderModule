package com.jagt.reader.shared.security.domain.port.output;

import java.util.Map;

public interface TokenProviderPort {
    String generateAccessToken(String username, Map<String, Object> claims);
    String generateRefreshToken(String username, Map<String, Object> claims);
    boolean validateToken(String token);
    String extractUsername(String token);
    String extractTokenType(String token);
    String extractIp(String jwt);
}
