package com.jagt.reader.favorite.infrastructure.output.persistence.adapter;

import com.jagt.reader.favorite.domain.model.Favorite;
import com.jagt.reader.favorite.domain.port.output.FavoritePersistencePort;
import com.jagt.reader.favorite.infrastructure.output.persistence.mapper.FavoritePersistenceMapper;
import com.jagt.reader.favorite.infrastructure.output.persistence.repository.FavoriteRepository;
import com.jagt.reader.shared.common.domain.model.value.IDValue;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FavoritePersistenceAdapter implements FavoritePersistencePort {
    private final FavoriteRepository repository;
    private final FavoritePersistenceMapper mapper;

    @Override
    public Favorite save(Favorite favorite) {
        return mapper.toDomain(repository.save(mapper.toEntity(favorite)));
    }

    @Override
    @Transactional
    public void deleteByUserIdAndMangaId(IDValue id, String mangaID) {
        repository.deleteByUserIdAndMangaId(id.getId(), mangaID);
    }

    @Override
    public List<Favorite> findAll(int offset, int limit) {

        return repository.findAll(PageRequest.of(offset, limit))
                .getContent()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public List<Favorite> findAllByUserId(Long id, int offset, int limit) {
        return repository.findAllByUser_Id(id, PageRequest.of(offset, limit))
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public long countByUserId(Long id) {
        return repository.countByUser_Id(id);
    }

    @Override
    public void deleteAllByUserID(IDValue userId) {
        repository.deleteAllByUser_Id(userId.getId());
    }
}
