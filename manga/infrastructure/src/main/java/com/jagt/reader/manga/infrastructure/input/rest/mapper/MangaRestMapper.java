package com.jagt.reader.manga.infrastructure.input.rest.mapper;

import com.jagt.reader.manga.application.query.GetFeedQuery;
import com.jagt.reader.manga.application.query.GetMangaByIDQuery;
import com.jagt.reader.manga.application.query.GetMangaQuery;
import com.jagt.reader.manga.domain.model.Chapter;
import com.jagt.reader.manga.domain.model.Feed;
import com.jagt.reader.manga.domain.model.Manga;
import com.jagt.reader.manga.infrastructure.input.rest.response.ChapterResponse;
import com.jagt.reader.manga.infrastructure.input.rest.response.FeedResponse;
import com.jagt.reader.manga.infrastructure.input.rest.response.MangaResponse;
import com.jagt.reader.manga.infrastructure.input.rest.response.attributes.FeedDTO;
import com.jagt.reader.shared.common.application.query.GetCommonQuery;
import com.jagt.reader.shared.common.domain.model.Pagination;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MangaRestMapper {
    FeedResponse toFeedResponse(Pagination<Feed> feedPaginatedResult);

    @Mapping(target = "attributes.volume", source = "volume")
    @Mapping(target = "attributes.chapter", source = "chapter")
    @Mapping(target = "attributes.title", source = "title")
    @Mapping(target = "attributes.language", source = "translatedLanguage")
    @Mapping(target = "attributes.pages", source = "pages")
    FeedDTO toFeedDTO(Feed feed);

    Pagination<MangaResponse> toResponse(Pagination<Manga> manga);
    MangaResponse toResponse(Manga manga);

    @Mapping(source = ".", target = "data", qualifiedByName = "buildData")
    @Mapping(source = ".", target = "dataSaver", qualifiedByName = "buildDataSaver")
    ChapterResponse toResponse(Chapter chapter);

    @Mapping(target = "pagination", expression = "java(mapToCommonQuery(offset, limit))")
    GetMangaQuery toMangaQuery(String title, int offset, int limit, boolean nsfw);

    GetMangaByIDQuery toQuery(String id);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "pagination", expression = "java(mapToCommonQuery(offset, limit))")
    @Mapping(target = "nsfw", source = "nsfw")
    GetFeedQuery toFeedQuery(String id, int offset, int limit, boolean nsfw);

    @Named("buildData")
    default List<String> buildData(Chapter chapter) {
        return buildUrl(chapter.getBaseUrl(), chapter.getHash(), chapter.getData(), "data");
    }

    @Named("buildDataSaver")
    default List<String> buildDataSaver(Chapter chapter) {
        return buildUrl(chapter.getBaseUrl(), chapter.getHash(), chapter.getDataSaver(), "data-saver");
    }

    default GetCommonQuery mapToCommonQuery(int offset, int limit) {
        return new GetCommonQuery(offset, limit);
    }

    private static List<String> buildUrl(String baseUrl, String hash, List<String> data, String type) {
        if (data == null || data.isEmpty()) return List.of();
        return data.stream()
                .map(img -> String.format("%s/%s/%s/%s", baseUrl, type, hash, img))
                .toList();
    }
}
