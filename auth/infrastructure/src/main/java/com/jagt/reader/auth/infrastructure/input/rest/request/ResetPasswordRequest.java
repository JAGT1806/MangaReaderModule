package com.jagt.reader.auth.infrastructure.input.rest.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResetPasswordRequest(
        @NotBlank(message = "${user.mail.error.blank}")
        String email,
        @NotBlank(message = "${security.code.error.blank}")
        String code,
        @NotBlank(message = "${user.new.password.error.blank}")
        @Size(min = 8, message = "${user.new.password.size.invalid}")
        String newPassword
) {
}
