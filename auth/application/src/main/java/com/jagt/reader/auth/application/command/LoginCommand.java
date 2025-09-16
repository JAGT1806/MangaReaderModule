package com.jagt.reader.auth.application.command;

public record LoginCommand(
        String email,
        String password
) {
}
