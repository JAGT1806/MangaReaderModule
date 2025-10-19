package com.jagt.reader.favorite.domain.port.output;

import com.jagt.reader.favorite.domain.model.Favorite;
import com.jagt.reader.shared.common.domain.model.value.IDValue;

import java.util.List;

public interface FavoritePersistencePort {
    Favorite save(Favorite favorite);

    void deleteByUserIdAndMangaId(IDValue id, String mangaID);

    List<Favorite> findAll(int offset, int limit);

    long count();

    List<Favorite> findAllByUserId(Long id, int offset, int limit);

    long countByUserId(Long id);

    void deleteAllByUserID(IDValue userId);
}
