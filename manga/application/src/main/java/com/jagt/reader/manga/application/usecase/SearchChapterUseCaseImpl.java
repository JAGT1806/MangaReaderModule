package com.jagt.reader.manga.application.usecase;

import com.jagt.reader.manga.application.port.input.SearchChapterUseCase;
import com.jagt.reader.manga.domain.model.Chapter;
import com.jagt.reader.manga.domain.port.output.MangaClientPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchChapterUseCaseImpl implements SearchChapterUseCase {
    private final MangaClientPort mangaClientPort;

    @Override
    public Chapter execute(String chapterId) {
        return mangaClientPort.searchChapterById(chapterId);
    }
}
