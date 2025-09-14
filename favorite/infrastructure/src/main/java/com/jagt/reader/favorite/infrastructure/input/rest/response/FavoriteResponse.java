package com.jagt.reader.favorite.infrastructure.input.rest.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteResponse {
    private Long id;
    private Long userId;
    private String mangaId;
    private LocalDateTime createdAt;
}
