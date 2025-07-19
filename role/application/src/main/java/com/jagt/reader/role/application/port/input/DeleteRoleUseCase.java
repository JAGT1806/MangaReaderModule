package com.jagt.reader.role.application.port.input;

import com.jagt.reader.shared.common.domain.model.value.IDValue;

public interface DeleteRoleUseCase {
    void execute(IDValue id);
}
