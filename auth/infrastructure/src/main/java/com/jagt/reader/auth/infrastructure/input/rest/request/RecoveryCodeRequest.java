package com.jagt.reader.auth.infrastructure.input.rest.request;

import jakarta.validation.constraints.NotBlank;

public record RecoveryCodeRequest(
        @NotBlank(message = "${user.email.error.blank}")
        String email
) {
}
