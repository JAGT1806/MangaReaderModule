package com.jagt.reader.auth.infrastructure.input.rest.request;

import jakarta.validation.constraints.NotBlank;

public record ActivateAccountRequest(
        @NotBlank(message = "${user.email.error.blank}")
        String email,
        @NotBlank(message = "${security.code.error.blank}")
        String code
) {
}
