package com.jagt.reader.favorite.application.port.input;

import com.jagt.reader.favorite.application.query.GetFavoriteByUserIdQuery;
import com.jagt.reader.favorite.domain.model.Favorite;
import com.jagt.reader.shared.common.application.query.GetCommonQuery;
import com.jagt.reader.shared.common.domain.model.Pagination;

public interface GetFavoriteManga {
    Pagination<Favorite> execute(GetCommonQuery query);
    Pagination<Favorite> execute(GetFavoriteByUserIdQuery query);
}
