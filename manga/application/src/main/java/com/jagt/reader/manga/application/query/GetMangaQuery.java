package com.jagt.reader.manga.application.query;

import com.jagt.reader.shared.common.application.query.GetCommonQuery;

public record GetMangaQuery(
        String title,
        GetCommonQuery pagination,
        boolean nsfw
) {
}
