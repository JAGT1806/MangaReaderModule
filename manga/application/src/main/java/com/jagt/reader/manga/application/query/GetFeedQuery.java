package com.jagt.reader.manga.application.query;

import com.jagt.reader.shared.common.application.query.GetCommonQuery;

public record GetFeedQuery(
        String id,
        GetCommonQuery pagination,
        boolean nsfw
) {
}
