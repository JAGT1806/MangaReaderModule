package com.jagt.reader.manga.domain.port.output;

import com.jagt.reader.manga.domain.model.Chapter;
import com.jagt.reader.manga.domain.model.Feed;
import com.jagt.reader.manga.domain.model.Manga;
import com.jagt.reader.shared.common.domain.model.Pagination;

import java.util.List;

public interface MangaClientPort {
    Manga searchMangaById(String id, List<String> languages);
    Pagination<Manga> searchMangas(String title, int offset, int limit, boolean nsfw, List<String> languages);
    Pagination<Feed> searchMangaFeeds(String mangaId, int offset, int limit, boolean nsfw, List<String> languages);
    Chapter searchChapterById(String chapterId);
}
