package com.jagt.reader.user.application.port.input;

import com.jagt.reader.shared.common.domain.model.Pagination;
import com.jagt.reader.user.application.query.GetUserFilterQuery;
import com.jagt.reader.user.domain.model.User;

public interface GetUserUseCase {
    Pagination<User> execute(GetUserFilterQuery query);
    User execute(Long userId);
    User execute(String email);
}
