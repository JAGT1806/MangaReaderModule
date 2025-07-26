package com.jagt.reader.user.infrastructure.input.rest.request;

public record CreateUserRequest(
        String username,
        String email,
        String password
) {
}
