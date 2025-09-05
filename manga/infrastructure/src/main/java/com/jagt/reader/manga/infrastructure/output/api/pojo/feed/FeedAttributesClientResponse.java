package com.jagt.reader.manga.infrastructure.output.api.pojo.feed;

public record FeedAttributesClientResponse(
        String volume,
        String chapter,
        String title,
        String translatedLanguage,
        int pages
) {
}
