package com.jagt.reader.role.application.query;

import com.jagt.reader.shared.common.application.query.GetCommonQuery;
import com.jagt.reader.shared.common.domain.model.value.NameValue;

public record GetRoleFilterQuery(
        NameValue roleName,
        GetCommonQuery pagination
) {
}
