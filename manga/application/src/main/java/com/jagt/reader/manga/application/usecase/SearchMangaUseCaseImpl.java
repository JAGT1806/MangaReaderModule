package com.jagt.reader.manga.application.usecase;

import com.jagt.reader.manga.application.port.input.SearchMangaUseCase;
import com.jagt.reader.manga.application.query.GetMangaByIDQuery;
import com.jagt.reader.manga.application.query.GetMangaQuery;
import com.jagt.reader.manga.application.util.LanguageUtils;
import com.jagt.reader.manga.domain.model.Manga;
import com.jagt.reader.manga.domain.port.output.MangaClientPort;
import com.jagt.reader.shared.common.domain.model.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchMangaUseCaseImpl implements SearchMangaUseCase {
    private final MangaClientPort mangaClientPort;

    @Override
    public Manga execute(GetMangaByIDQuery query) {
        List<String> availableTranslatedLanguage = LanguageUtils.getAvailableTranslatedLanguages();
        return mangaClientPort.searchMangaById(query.id(), availableTranslatedLanguage);
    }

    @Override
    public Pagination<Manga> execute(GetMangaQuery query) {
        List<String> availableTranslatedLanguage = LanguageUtils.getAvailableTranslatedLanguages();
        return mangaClientPort.searchMangas(query.title(), query.pagination().offset(), query.pagination().limit(), query.nsfw(),availableTranslatedLanguage);
    }
}
