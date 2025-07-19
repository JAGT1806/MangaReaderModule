package com.jagt.reader.role.infrastructure.input.rest.response;

import java.time.LocalDateTime;

public record RoleResponse(
        Long id,
        String roleName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
