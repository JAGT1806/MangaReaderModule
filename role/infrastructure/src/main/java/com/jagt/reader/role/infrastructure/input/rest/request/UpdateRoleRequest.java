package com.jagt.reader.role.infrastructure.input.rest.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateRoleRequest(
        @NotBlank(message = "{role.name.error.blank}")
        String roleName
) {
}
