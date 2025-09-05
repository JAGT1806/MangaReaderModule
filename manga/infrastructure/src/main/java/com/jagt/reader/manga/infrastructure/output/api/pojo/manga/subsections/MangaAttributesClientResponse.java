package com.jagt.reader.manga.infrastructure.output.api.pojo.manga.subsections;

import java.util.Map;

public record MangaAttributesClientResponse(
        Map<String, String> title,
        Map<String, String> description
) {
}
