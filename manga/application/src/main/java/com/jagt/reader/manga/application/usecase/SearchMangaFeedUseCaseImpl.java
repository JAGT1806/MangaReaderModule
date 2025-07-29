package com.jagt.reader.manga.application.usecase;

import com.jagt.reader.manga.application.port.input.SearchMangaFeedUseCase;
import com.jagt.reader.manga.application.query.GetFeedQuery;
import com.jagt.reader.manga.application.util.LanguageUtils;
import com.jagt.reader.manga.domain.model.Feed;
import com.jagt.reader.manga.domain.port.output.MangaClientPort;
import com.jagt.reader.shared.common.domain.model.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchMangaFeedUseCaseImpl implements SearchMangaFeedUseCase {
    private final MangaClientPort mangaClientPort;

    @Override
    public Pagination<Feed> execute(GetFeedQuery query) {
        List<String> availableTranslatedLanguage = LanguageUtils.getAvailableTranslatedLanguages();

        return mangaClientPort.searchMangaFeeds(query.id(), query.pagination().offset(), query.pagination().limit(), query.nsfw(),availableTranslatedLanguage);
    }
}
