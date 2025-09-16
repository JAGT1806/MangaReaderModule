package com.jagt.reader.auth.application.port.input;

import com.jagt.reader.user.domain.model.User;

public interface GetUserByCodeUseCase {
    User execute(String code, Long userId);
}
