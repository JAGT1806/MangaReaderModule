package com.jagt.reader.user.infrastructure.input.rest.request;

import jakarta.validation.constraints.NotBlank;

public record UpdatePasswordRequest(
        @NotBlank
        String password,
        @NotBlank
        String newPassword
) {
}
