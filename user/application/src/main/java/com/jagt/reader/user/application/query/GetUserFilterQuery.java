package com.jagt.reader.user.application.query;

import com.jagt.reader.shared.common.application.query.GetCommonQuery;
import com.jagt.reader.shared.common.domain.model.value.NameValue;

public record GetUserFilterQuery(
        NameValue username,
        NameValue email,
        NameValue role,
        Boolean enabled,
        GetCommonQuery pagination
) {
}
