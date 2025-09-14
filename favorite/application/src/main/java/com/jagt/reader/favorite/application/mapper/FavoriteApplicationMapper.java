package com.jagt.reader.favorite.application.mapper;

import com.jagt.reader.favorite.application.command.AddFavoriteCommand;
import com.jagt.reader.favorite.domain.model.Favorite;
import com.jagt.reader.shared.common.application.query.GetCommonQuery;
import com.jagt.reader.shared.common.domain.model.Pagination;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FavoriteApplicationMapper {
    @Mapping(target = "user.id.id", source = "userId.id")
    @Mapping(target = "manga.mangaID", source = "mangaId")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "user.userValue", ignore = true)
    @Mapping(target = "user.auditTimestamps", ignore = true)
    @Mapping(target = "user.profilePicture", ignore = true)
    @Mapping(target = "user.roles", ignore = true)
    @Mapping(target = "user.enabled", ignore = true)
    Favorite toModel(AddFavoriteCommand command);

    @Mapping(target = "data", source = "models")
    @Mapping(target = "offset", source = "query.offset")
    @Mapping(target = "limit", source = "query.limit")
    Pagination<Favorite> toPagination(List<Favorite> models, GetCommonQuery query, long total);
}
