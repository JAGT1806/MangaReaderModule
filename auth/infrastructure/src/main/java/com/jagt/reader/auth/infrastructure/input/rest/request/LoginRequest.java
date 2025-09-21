package com.jagt.reader.auth.infrastructure.input.rest.request;

public record LoginRequest(
        String email,
        String password
) {
}
