package com.jagt.reader.favorite.infrastructure.output.persistence.mapper;

import com.jagt.reader.favorite.domain.model.Favorite;
import com.jagt.reader.favorite.infrastructure.output.persistence.entity.FavoriteEntity;
import com.jagt.reader.user.infrastructure.output.persistence.mapper.UserPersistenceMapper;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = UserPersistenceMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface FavoritePersistenceMapper {
    @Mapping(target = "id", source = "id.id")
    @Mapping(target = "mangaId", source = "manga.mangaID")
    FavoriteEntity toEntity(Favorite favorite);

    @Mapping(target = "id.id", source = "id")
    @Mapping(target = "manga.mangaID", source = "mangaId")
    Favorite toDomain(FavoriteEntity entity);
}
