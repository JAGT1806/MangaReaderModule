package com.jagt.reader.auth.infrastructure.input.rest.request;

public record ResetPasswordRequest(
        String email,
        String code,
        String newPassword
) {
}
