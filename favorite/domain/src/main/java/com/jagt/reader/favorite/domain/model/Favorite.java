package com.jagt.reader.favorite.domain.model;

import com.jagt.reader.favorite.domain.model.value.MangaValue;
import com.jagt.reader.shared.common.domain.model.value.IDValue;
import com.jagt.reader.user.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Favorite {
    private IDValue id;
    private MangaValue manga;
    private User user;
    private LocalDateTime createdAt;
}
