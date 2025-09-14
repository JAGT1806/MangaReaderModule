package com.jagt.reader.favorite.application.usecase;

import com.jagt.reader.favorite.application.port.input.DeleteFavoriteMangaUseCase;
import com.jagt.reader.favorite.domain.model.Favorite;
import com.jagt.reader.favorite.domain.port.output.FavoritePersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteFavoriteMangaUseCaseImpl implements DeleteFavoriteMangaUseCase {
    private final FavoritePersistencePort persistencePort;
    @Override
    public void execute(Favorite favorite) {
        persistencePort.deleteByUserIdAndMangaId(favorite.getUser().getId(), favorite.getManga().getMangaID());
    }
}
