package com.jagt.reader.auth.application.command;

public record GenerateRecoveryCodeCommand(
        String email
) {
}
