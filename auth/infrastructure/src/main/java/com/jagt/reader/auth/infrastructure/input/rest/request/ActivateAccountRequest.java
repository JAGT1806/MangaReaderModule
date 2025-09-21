package com.jagt.reader.auth.infrastructure.input.rest.request;

public record ActivateAccountRequest(
        String email,
        String code
) {
}
