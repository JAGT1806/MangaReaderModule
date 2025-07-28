package com.jagt.reader.user.infrastructure.input.rest.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @NotBlank(message = "${user.name.error.blank}")
        String username,
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "${user.mail.format.not.valid}")
        @NotBlank(message = "${user.mail.error.blank}")
        String email,
        @NotBlank(message = "${user.password.error.blank}")
        @Size(min = 8, message = "${user.password.size.invalid}")
        String password
) {
}
