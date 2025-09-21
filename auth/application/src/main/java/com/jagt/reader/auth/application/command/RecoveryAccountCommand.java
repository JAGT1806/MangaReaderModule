package com.jagt.reader.auth.application.command;

public record RecoveryAccountCommand(
        String email,
        String code,
        String newPassword
) {
}
