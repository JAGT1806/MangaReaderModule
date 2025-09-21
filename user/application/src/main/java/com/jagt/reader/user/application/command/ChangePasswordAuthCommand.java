package com.jagt.reader.user.application.command;

import com.jagt.reader.shared.common.domain.model.value.IDValue;

public record ChangePasswordAuthCommand(
        IDValue userID,
        String newPassword
) {
}
