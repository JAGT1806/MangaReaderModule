package com.jagt.reader.auth.infrastructure.input.rest.request;

public record RegisterRequest(
        String username,
        String email,
        String password
) {
}
