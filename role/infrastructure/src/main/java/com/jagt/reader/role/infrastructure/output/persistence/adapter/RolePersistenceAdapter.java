package com.jagt.reader.role.infrastructure.output.persistence.adapter;

import com.jagt.reader.role.domain.model.Role;
import com.jagt.reader.role.domain.port.output.RolePersistencePort;
import com.jagt.reader.role.infrastructure.output.persistence.mapper.RolePersistenceMapper;
import com.jagt.reader.role.infrastructure.output.persistence.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RolePersistenceAdapter implements RolePersistencePort {
    private final RoleRepository repository;
    private final RolePersistenceMapper mapper;

    @Override
    public List<Role> findAll(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        return repository.findAll(pageable).getContent()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Role> findByFilter(String name, int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        return repository.findByNameContaining(name, pageable)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Role> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Role> findByName(String name) {
        return repository.findByName(name)
                .map(mapper::toDomain);
    }

    @Override
    public boolean existByName(String name) {
        return repository.existsByName(name);
    }

    @Override
    public Role save(Role role) {
        return mapper.toDomain(repository.save(mapper.toEntity(role)));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public long countByFilter(String name) {
        return repository.countByNameContaining(name);
    }
}
