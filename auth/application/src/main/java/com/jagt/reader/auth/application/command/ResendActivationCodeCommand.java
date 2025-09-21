package com.jagt.reader.auth.application.command;

public record ResendActivationCodeCommand(
        String email
) {
}
