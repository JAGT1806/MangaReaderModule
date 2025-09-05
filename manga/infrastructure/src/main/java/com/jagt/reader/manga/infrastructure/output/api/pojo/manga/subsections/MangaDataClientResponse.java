package com.jagt.reader.manga.infrastructure.output.api.pojo.manga.subsections;

import java.util.List;

public record MangaDataClientResponse(
        String id,
        MangaAttributesClientResponse attributes,
        List<MangaRelationshipClientResponse> relationships
) {
}
