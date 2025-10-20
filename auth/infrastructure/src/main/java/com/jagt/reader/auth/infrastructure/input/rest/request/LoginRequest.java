package com.jagt.reader.auth.infrastructure.input.rest.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "${user.email.error.blank}")
        String email,
        @NotBlank(message = "${user.password.error.blank}")
        String password
) {
}
