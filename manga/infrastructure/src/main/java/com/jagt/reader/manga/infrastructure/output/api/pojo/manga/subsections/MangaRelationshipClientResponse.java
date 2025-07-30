package com.jagt.reader.manga.infrastructure.output.api.pojo.manga.subsections;


public record MangaRelationshipClientResponse(
        String id,
        String type,
        CoverAttributesClientResponse attributes
) {
}
