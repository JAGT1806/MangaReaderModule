package com.jagt.reader.favorite.application.command;

import com.jagt.reader.shared.common.domain.model.value.IDValue;

public record AddFavoriteCommand(
        IDValue userId,
        String mangaId
) {
}
