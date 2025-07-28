package com.jagt.reader.user.infrastructure.input.rest.response;

import java.time.LocalDateTime;
import java.util.Set;

public record UserResponse(
        Long id,
        String username,
        String email,
        boolean enabled,
        Set<String> roles,
        String profilePictureUrl,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
