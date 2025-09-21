package com.jagt.reader.auth.application.command;

public record ActivateAccountCommand(
        String email,
        String code
) {
}
