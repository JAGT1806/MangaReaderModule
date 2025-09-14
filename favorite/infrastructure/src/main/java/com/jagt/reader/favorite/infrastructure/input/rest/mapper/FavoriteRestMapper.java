package com.jagt.reader.favorite.infrastructure.input.rest.mapper;

import com.jagt.reader.favorite.application.command.AddFavoriteCommand;
import com.jagt.reader.favorite.application.query.GetFavoriteByUserIdQuery;
import com.jagt.reader.favorite.domain.model.Favorite;
import com.jagt.reader.favorite.infrastructure.input.rest.request.AddFavoriteRequest;
import com.jagt.reader.favorite.infrastructure.input.rest.request.DeleteFavoriteRequest;
import com.jagt.reader.favorite.infrastructure.input.rest.response.FavoriteListResponse;
import com.jagt.reader.favorite.infrastructure.input.rest.response.FavoriteResponse;
import com.jagt.reader.shared.common.application.query.GetCommonQuery;
import com.jagt.reader.shared.common.domain.model.Pagination;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FavoriteRestMapper {
    FavoriteListResponse toResponse(Pagination<Favorite> models);

    @Mapping(target = "id", source = "id.id")
    @Mapping(target = "userId", source = "user.id.id")
    @Mapping(target = "mangaId", source = "manga.mangaID")
    FavoriteResponse toResponse(Favorite favorite);

    default GetCommonQuery toQuery(int offset, int limit) {
        return new GetCommonQuery(offset, limit);
    }

    @Mapping(target = "userID.id", source = "userId")
    @Mapping(target = "query", expression = "java(toQuery(offset, limit))")
    GetFavoriteByUserIdQuery toQuery(Long userId, int offset, int limit);

    @Mapping(target = "userId.id", source = "userId")
    @Mapping(target = "mangaId", source = "request.mangaId")
    AddFavoriteCommand toCommand(Long userId, AddFavoriteRequest request);

    @Mapping(target = "user.id.id", source = "userId")
    @Mapping(target = "manga.mangaID", source = "request.mangaId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Favorite toDomain(Long userId, DeleteFavoriteRequest request);
}
