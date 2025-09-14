package com.jagt.reader.favorite.infrastructure.input.rest.controller;

import com.jagt.reader.favorite.application.port.input.AddFavoriteMangaUseCase;
import com.jagt.reader.favorite.application.port.input.DeleteFavoriteMangaUseCase;
import com.jagt.reader.favorite.application.port.input.GetFavoriteMangaUseCase;
import com.jagt.reader.favorite.infrastructure.input.rest.controller.doc.FavoriteControllerDoc;
import com.jagt.reader.favorite.infrastructure.input.rest.mapper.FavoriteRestMapper;
import com.jagt.reader.favorite.infrastructure.input.rest.request.AddFavoriteRequest;
import com.jagt.reader.favorite.infrastructure.input.rest.request.DeleteFavoriteRequest;
import com.jagt.reader.favorite.infrastructure.input.rest.response.FavoriteListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController implements FavoriteControllerDoc {
    private final GetFavoriteMangaUseCase getFavoriteMangaUseCase;
    private final AddFavoriteMangaUseCase addFavoriteMangaUseCase;
    private final DeleteFavoriteMangaUseCase deleteFavoriteMangaUseCase;
    private final FavoriteRestMapper mapper;

    @Override
    public ResponseEntity<FavoriteListResponse> getAllFavorites(int offset, int limit) {
        return ResponseEntity.ok(
                mapper.toResponse(getFavoriteMangaUseCase.execute(mapper.toQuery(offset, limit)))
        );
    }

    @Override
    public ResponseEntity<FavoriteListResponse> getFavoritesByUserId(Long userId, int offset, int limit) {
        return ResponseEntity.ok(
                mapper.toResponse(getFavoriteMangaUseCase.execute(mapper.toQuery(userId, offset, limit)))
        );
    }

    @Override
    public ResponseEntity<Void> addFavorite(Long userId, AddFavoriteRequest request) {
        addFavoriteMangaUseCase.execute(mapper.toCommand(userId, request));
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteFavorite(Long userId, DeleteFavoriteRequest request) {
        deleteFavoriteMangaUseCase.execute(mapper.toDomain(userId, request));
        return ResponseEntity.noContent().build();
    }
}
