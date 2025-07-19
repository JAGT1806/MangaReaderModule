package com.jagt.reader.role.application.command;

import com.jagt.reader.shared.common.domain.model.value.NameValue;

public record CreateRoleCommand(
        NameValue roleName
) {
}
