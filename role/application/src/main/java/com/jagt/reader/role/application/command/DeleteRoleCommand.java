package com.jagt.reader.role.application.command;

import com.jagt.reader.shared.common.domain.model.value.IDValue;

public record DeleteRoleCommand(
        IDValue roleId
) {
}
