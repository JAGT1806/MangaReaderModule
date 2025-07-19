package com.jagt.reader.role.application.port.input;

import com.jagt.reader.role.application.query.GetRoleFilterQuery;
import com.jagt.reader.role.domain.model.Role;
import com.jagt.reader.shared.common.domain.model.Pagination;
import com.jagt.reader.shared.common.domain.model.value.IDValue;

public interface GetRoleUseCase {
    Pagination<Role> execute(GetRoleFilterQuery query);
    Role execute(IDValue roleId);
}
