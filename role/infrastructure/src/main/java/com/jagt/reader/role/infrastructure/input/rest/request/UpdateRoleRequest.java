package com.jagt.reader.role.infrastructure.input.rest.request;

import jakarta.validation.constraints.NotNull;

public record UpdateRoleRequest(
        @NotNull(message = "{role.name.error.null}")
        String roleName
) {
}
