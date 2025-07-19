package com.jagt.reader.role.application.validation.output;

import com.jagt.reader.shared.common.domain.model.value.IDValue;

public interface RoleUsageChecker {
    boolean isRoleInUse(IDValue roleId);
}
