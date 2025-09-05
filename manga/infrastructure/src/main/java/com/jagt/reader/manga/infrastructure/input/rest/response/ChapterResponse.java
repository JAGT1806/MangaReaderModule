package com.jagt.reader.manga.infrastructure.input.rest.response;

import java.util.List;

public record ChapterResponse(
        List<String> data,
        List<String> dataSaver
) {
}
