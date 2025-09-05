package com.jagt.reader.manga.infrastructure.input.rest.response.attributes;

public record FeedAttributesDTO(
        String volume,
        String chapter,
        String title,
        String language,
        int pages
) {
}
