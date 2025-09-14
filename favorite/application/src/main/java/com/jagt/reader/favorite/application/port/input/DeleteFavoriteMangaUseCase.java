package com.jagt.reader.favorite.application.port.input;

import com.jagt.reader.favorite.domain.model.Favorite;

public interface DeleteFavoriteMangaUseCase {
    void execute(Favorite favorite);
}
