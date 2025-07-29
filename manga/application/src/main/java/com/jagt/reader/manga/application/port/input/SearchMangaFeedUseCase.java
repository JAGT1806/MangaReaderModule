package com.jagt.reader.manga.application.port.input;

import com.jagt.reader.manga.application.query.GetFeedQuery;
import com.jagt.reader.manga.domain.model.Feed;
import com.jagt.reader.shared.common.domain.model.Pagination;

public interface SearchMangaFeedUseCase {
    Pagination<Feed> execute(GetFeedQuery query);
}
