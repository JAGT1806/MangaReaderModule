package com.jagt.reader.manga.application.port.input;

import com.jagt.reader.manga.application.query.GetMangaByIDQuery;
import com.jagt.reader.manga.application.query.GetMangaQuery;
import com.jagt.reader.manga.domain.model.Manga;
import com.jagt.reader.shared.common.domain.model.Pagination;

public interface SearchMangaUseCase {
    Manga execute(GetMangaByIDQuery query);
    Pagination<Manga> execute(GetMangaQuery query);
}
