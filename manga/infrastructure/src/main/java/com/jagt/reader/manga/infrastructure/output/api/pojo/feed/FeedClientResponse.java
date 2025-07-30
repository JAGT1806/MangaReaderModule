package com.jagt.reader.manga.infrastructure.output.api.pojo.feed;

public record FeedClientResponse(
        String id,
        FeedAttributesClientResponse attributes
) {
}
