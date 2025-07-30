package com.jagt.reader.manga.infrastructure.output.api.adapter;

import com.jagt.reader.manga.domain.model.Chapter;
import com.jagt.reader.manga.domain.model.Feed;
import com.jagt.reader.manga.domain.model.Manga;
import com.jagt.reader.manga.domain.port.output.MangaClientPort;
import com.jagt.reader.manga.infrastructure.output.api.client.MangaDexClient;
import com.jagt.reader.manga.infrastructure.output.api.mapper.MangaClientMapper;
import com.jagt.reader.shared.common.domain.model.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.jagt.reader.manga.domain.model.constant.Constant.NSFW_CONTENT;
import static com.jagt.reader.manga.domain.model.constant.Constant.SAFE_CONTENT;

@Component
@RequiredArgsConstructor
public class MangaClientAdapter implements MangaClientPort {
    private final MangaDexClient mangaDexClient;
    private final MangaClientMapper mapper;

    private static final String DEFAULT_INCLUDES = "cover_art";

    @Override
    public Manga searchMangaById(String id, List<String> languages) {
        return mapper.toManga(
                mangaDexClient.getMangaId(id, DEFAULT_INCLUDES), languages
        );
    }

    @Override
    public Pagination<Manga> searchMangas(String title, int offset, int limit, boolean nsfw, List<String> languages) {
        List<String> contentRatingFilters = toRating(nsfw);

        return mapper.toPaginatedResult(mangaDexClient.getSearchManga(title, DEFAULT_INCLUDES, offset, limit, contentRatingFilters, languages), languages);
    }

        @Override
    public Pagination<Feed> searchMangaFeeds(String mangaId, int offset, int limit, boolean nsfw, List<String> languages) {
        List<String> contentRating = toRating(nsfw);
        return mapper.toPaginatedResult(
                mangaDexClient.getMangaIdFeed(mangaId, offset, limit, contentRating, null, "asc", "asc", languages)
        );
    }

    @Override
    public Chapter searchChapterById(String chapterId) {
        return mapper.toChapter(mangaDexClient.getAtHomeServerChapterId(chapterId));
    }

    private List<String> toRating(boolean nsfw) {
        List<String> contentRatingFilters = new ArrayList<>(SAFE_CONTENT);
        if(nsfw) contentRatingFilters.addAll(NSFW_CONTENT);

        return contentRatingFilters;
    }
}
