package com.jagt.reader.user.application.command;

import com.jagt.reader.shared.common.domain.model.value.IDValue;

public record UpdatePasswordCommand(
        IDValue userId,
        String password,
        String newPassword
) {
}
