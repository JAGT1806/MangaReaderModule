package com.jagt.reader.user.application.port.input;

import com.jagt.reader.shared.common.domain.model.value.IDValue;

public interface DeleteUserUseCase {
    void execute(IDValue userId);
}
