package com.jagt.reader.manga.application.port.input;

import com.jagt.reader.manga.domain.model.Chapter;

public interface SearchChapterUseCase {
    Chapter execute(String id);
}
