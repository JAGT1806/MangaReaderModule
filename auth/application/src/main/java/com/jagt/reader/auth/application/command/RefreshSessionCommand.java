package com.jagt.reader.auth.application.command;

public record RefreshSessionCommand(
        String refreshToken
) {
}
