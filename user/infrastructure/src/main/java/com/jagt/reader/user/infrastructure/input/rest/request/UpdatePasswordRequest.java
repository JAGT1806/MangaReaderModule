package com.jagt.reader.user.infrastructure.input.rest.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePasswordRequest(
        @NotBlank(message = "${user.password.error.blank}")
        String password,
        @NotBlank(message = "${user.new.password.error.blank}")
        @Size(min = 8, message = "${user.new.password.size.invalid}")
        String newPassword
) {
}
