package com.jagt.reader.manga.infrastructure.output.api.pojo.chapter;

public record ChapterClientResponse(
        String baseUrl,
        ChapterAttributesClientResponse chapter
) {
}
