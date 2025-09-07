package com.jagt.reader.favorite.application.usecase;

import com.jagt.reader.favorite.application.command.AddFavoriteCommand;
import com.jagt.reader.favorite.application.mapper.FavoriteApplicationMapper;
import com.jagt.reader.favorite.application.port.input.AddFavoriteManga;
import com.jagt.reader.favorite.domain.model.Favorite;
import com.jagt.reader.favorite.domain.port.output.FavoritePersistencePort;
import com.jagt.reader.user.application.port.input.GetUserUseCase;
import com.jagt.reader.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddFavoriteMangaImpl implements AddFavoriteManga {
    private final FavoritePersistencePort persistencePort;
    private final FavoriteApplicationMapper mapper;
    private final GetUserUseCase getUserUseCase;

    @Override
    public void execute(AddFavoriteCommand command) {
        User user = getUserUseCase.execute(command.mangaId());
        Favorite model = mapper.toModel(command);
        model.setUser(user);

        persistencePort.save(model);
    }
}
