package com.jagt.reader.manga.infrastructure.input.rest.response;

public record MangaResponse(
        String id,
        String title,
        String description,
        String coverId,
        String fileName
) {
}
