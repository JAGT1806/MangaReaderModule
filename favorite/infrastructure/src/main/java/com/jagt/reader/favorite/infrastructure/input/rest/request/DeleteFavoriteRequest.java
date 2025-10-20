package com.jagt.reader.favorite.infrastructure.input.rest.request;

import jakarta.validation.constraints.NotBlank;

public record DeleteFavoriteRequest(
        @NotBlank(message = "${favorite.manga.name.error.blank}")
        String mangaId
) {
}
