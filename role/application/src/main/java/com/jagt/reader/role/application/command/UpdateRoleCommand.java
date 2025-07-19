package com.jagt.reader.role.application.command;

import com.jagt.reader.shared.common.domain.model.value.IDValue;
import com.jagt.reader.shared.common.domain.model.value.NameValue;

public record UpdateRoleCommand(
        IDValue roleId,
        NameValue roleName
) {
}
