package com.jagt.reader.favorite.application.query;

import com.jagt.reader.shared.common.application.query.GetCommonQuery;
import com.jagt.reader.shared.common.domain.model.value.IDValue;

public record GetFavoriteByUserIdQuery(
        IDValue userID,
        GetCommonQuery query
) {
}
