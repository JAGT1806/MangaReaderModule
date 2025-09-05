package com.jagt.reader.manga.infrastructure.input.rest.response;

import com.jagt.reader.manga.infrastructure.input.rest.response.attributes.FeedDTO;

import java.util.List;

public record FeedResponse(
        List<FeedDTO> data,
        int offset,
        int limit,
        long total
) {
}
