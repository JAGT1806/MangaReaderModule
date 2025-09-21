package com.jagt.reader.user.application.command;

import com.jagt.reader.shared.common.domain.model.value.IDValue;

public record ChangeEnabledUseCommand(
        IDValue userId,
        boolean enabled
) {
}
