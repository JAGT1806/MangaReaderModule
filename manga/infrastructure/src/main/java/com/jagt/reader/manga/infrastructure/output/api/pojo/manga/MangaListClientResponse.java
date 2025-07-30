package com.jagt.reader.manga.infrastructure.output.api.pojo.manga;

import com.jagt.reader.manga.infrastructure.output.api.pojo.manga.subsections.MangaDataClientResponse;

import java.util.List;

public record MangaListClientResponse(
        List<MangaDataClientResponse> data,
        int offset,
        int limit,
        long total
) {
}
