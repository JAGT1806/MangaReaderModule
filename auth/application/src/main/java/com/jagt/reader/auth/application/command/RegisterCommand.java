package com.jagt.reader.auth.application.command;

public record RegisterCommand(
        String username,
        String email,
        String password
) {
}
