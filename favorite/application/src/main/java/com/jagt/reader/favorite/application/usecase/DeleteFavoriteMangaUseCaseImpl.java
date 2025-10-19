package com.jagt.reader.favorite.application.usecase;

import com.jagt.reader.favorite.application.port.input.DeleteFavoriteMangaUseCase;
import com.jagt.reader.favorite.domain.model.Favorite;
import com.jagt.reader.favorite.domain.port.output.FavoritePersistencePort;
import com.jagt.reader.role.application.validation.input.Validation;
import com.jagt.reader.shared.common.domain.model.value.IDValue;
import com.jagt.reader.user.application.port.input.DeleteAllFavoritesByUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteFavoriteMangaUseCaseImpl implements DeleteFavoriteMangaUseCase, DeleteAllFavoritesByUserUseCase {
    private final FavoritePersistencePort persistencePort;
    private final Validation validation;

    @Override
    public void execute(Favorite favorite) {
        persistencePort.deleteByUserIdAndMangaId(favorite.getUser().getId(), favorite.getManga().getMangaID());
    }

    @Override
    public void execute(IDValue userId) {
        validation.validateNull(userId);
        persistencePort.deleteAllByUserID(userId);
    }
}
