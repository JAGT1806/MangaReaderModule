package com.jagt.reader.manga.infrastructure.output.api.pojo.manga;

import com.jagt.reader.manga.infrastructure.output.api.pojo.manga.subsections.MangaDataClientResponse;

public record MangaClientResponse(
        MangaDataClientResponse data
) {
}
