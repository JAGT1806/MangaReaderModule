package com.jagt.reader.favorite.application.usecase;

import com.jagt.reader.favorite.application.mapper.FavoriteApplicationMapper;
import com.jagt.reader.favorite.application.port.input.GetFavoriteManga;
import com.jagt.reader.favorite.application.query.GetFavoriteByUserIdQuery;
import com.jagt.reader.favorite.domain.model.Favorite;
import com.jagt.reader.favorite.domain.port.output.FavoritePersistencePort;
import com.jagt.reader.shared.common.application.query.GetCommonQuery;
import com.jagt.reader.shared.common.domain.model.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetFavoriteMangaImpl implements GetFavoriteManga {
    private final FavoritePersistencePort persistencePort;
    private final FavoriteApplicationMapper mapper;

    @Override
    public Pagination<Favorite> execute(GetCommonQuery query) {
        List<Favorite> favorites = persistencePort.findAll(query.offset(), query.limit());
        long total = persistencePort.count();
        return mapper.toPagination(favorites, query, total);
    }

    @Override
    public Pagination<Favorite> execute(GetFavoriteByUserIdQuery query) {
        List<Favorite> favorites = persistencePort.findAllByUserId(query.userID().getId(), query.query().offset(), query.query().limit());
        long total = persistencePort.countByUserId(query.userID().getId());
        return mapper.toPagination(favorites, query.query(), total);
    }
}
