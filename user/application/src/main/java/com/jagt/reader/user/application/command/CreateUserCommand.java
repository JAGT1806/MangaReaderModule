package com.jagt.reader.user.application.command;

import com.jagt.reader.shared.common.domain.model.value.NameValue;

public record CreateUserCommand(
        NameValue username,
        NameValue email,
        String password
) {
}
