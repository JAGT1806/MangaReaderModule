package com.jagt.reader.manga.infrastructure.output.api.pojo.chapter;

import java.util.List;

public record ChapterAttributesClientResponse(
        String hash,
        List<String> data,
        List<String> dataSaver
) {
}
