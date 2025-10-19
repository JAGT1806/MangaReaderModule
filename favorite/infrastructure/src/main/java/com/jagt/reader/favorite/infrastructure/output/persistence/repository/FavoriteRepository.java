package com.jagt.reader.favorite.infrastructure.output.persistence.repository;

import com.jagt.reader.favorite.infrastructure.output.persistence.entity.FavoriteEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<FavoriteEntity, Long> {
    void deleteByUserIdAndMangaId(Long userId, String mangaId);

    List<FavoriteEntity> findAllByUser_Id(Long userId, Pageable pageable);

    long countByUser_Id(Long userId);

    void deleteAllByUser_Id(Long userId);
}
