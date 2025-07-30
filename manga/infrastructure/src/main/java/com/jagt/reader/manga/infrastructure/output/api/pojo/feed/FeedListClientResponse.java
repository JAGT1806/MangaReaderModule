package com.jagt.reader.manga.infrastructure.output.api.pojo.feed;

import java.util.List;

public record FeedListClientResponse(
        List<FeedClientResponse> data,
        int offset,
        int limit,
        long total
) {
}
