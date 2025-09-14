package com.jagt.reader.favorite.infrastructure.input.rest.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteListResponse {
    private List<FavoriteResponse> data;
    private int offset;
    private int limit;
    private long total;
}
