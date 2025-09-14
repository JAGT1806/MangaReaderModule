package com.jagt.reader.favorite.application.port.input;

import com.jagt.reader.favorite.application.command.AddFavoriteCommand;

public interface AddFavoriteMangaUseCase {
    void execute(AddFavoriteCommand command);
}
