package com.jagt.reader.auth.infrastructure.input.rest.request;

import jakarta.validation.constraints.NotBlank;

public record ResendActivationCodeRequest(
        @NotBlank(message = "${user.mail.error.blank}")
        String email
) {
}
