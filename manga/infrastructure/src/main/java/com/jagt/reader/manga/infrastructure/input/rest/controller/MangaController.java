package com.jagt.reader.manga.infrastructure.input.rest.controller;

import com.jagt.reader.manga.application.port.input.SearchChapterUseCase;
import com.jagt.reader.manga.application.port.input.SearchMangaFeedUseCase;
import com.jagt.reader.manga.application.port.input.SearchMangaUseCase;
import com.jagt.reader.manga.infrastructure.input.rest.controller.doc.MangaControllerDoc;
import com.jagt.reader.manga.infrastructure.input.rest.mapper.MangaRestMapper;
import com.jagt.reader.manga.infrastructure.input.rest.response.ChapterResponse;
import com.jagt.reader.manga.infrastructure.input.rest.response.FeedResponse;
import com.jagt.reader.manga.infrastructure.input.rest.response.MangaResponse;
import com.jagt.reader.shared.common.domain.model.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mangas")
@RequiredArgsConstructor
public class MangaController implements MangaControllerDoc {
    private final MangaRestMapper mapper;
    private final SearchMangaUseCase searchMangaUseCase;
    private final SearchMangaFeedUseCase searchMangaFeedUseCase;
    private final SearchChapterUseCase searchChapterUseCase;

    @Override
    public ResponseEntity<Pagination<MangaResponse>> getMangas(String title, int offset, int limit, boolean nsfw) {
        return ResponseEntity.ok(
                mapper.toResponse(
                        searchMangaUseCase.execute(
                                mapper.toMangaQuery(title, offset, limit, nsfw)
                        )
                )
        );
    }

    @Override
    public ResponseEntity<MangaResponse> getMangaById(String id) {
        return ResponseEntity.ok(
                mapper.toResponse(searchMangaUseCase.execute(
                        mapper.toQuery(id)
                ))
        );
    }

    @Override
    public ResponseEntity<FeedResponse> getMangaFeeds(String id, int offset, int limit, boolean nsfw) {
        return ResponseEntity.ok(
                mapper.toFeedResponse(
                        searchMangaFeedUseCase.execute(mapper.toFeedQuery(id, offset, limit, nsfw))
                )
        );
    }

    @Override
    public ResponseEntity<ChapterResponse> getChapter(String id) {
        return ResponseEntity.ok(
                mapper.toResponse(
                        searchChapterUseCase.execute(id)
                )
        );
    }
}
