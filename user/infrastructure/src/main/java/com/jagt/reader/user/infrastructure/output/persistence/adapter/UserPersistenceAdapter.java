package com.jagt.reader.user.infrastructure.output.persistence.adapter;

import com.jagt.reader.shared.common.domain.model.value.IDValue;
import com.jagt.reader.user.domain.model.User;
import com.jagt.reader.user.domain.port.output.UserPersistencePort;
import com.jagt.reader.user.infrastructure.output.persistence.mapper.UserPersistenceMapper;
import com.jagt.reader.user.infrastructure.output.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPersistencePort {
    private final UserRepository userRepository;
    private final UserPersistenceMapper mapper;

    @Override
    public User save(User user) {
        return mapper.toDomain(userRepository.save(mapper.toEntity(user)));
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(mapper::toDomain);
    }

    @Override
    public List<User> findByFilters(String username, String email, String role, Boolean enabled, int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        return userRepository.findByFilters(username, email, role, enabled, pageable)
                .stream().map(mapper::toDomain)
                .toList();
    }

    @Override
    public long countByFilters(String username, String email, String role, Boolean enabled) {
        return userRepository.countByFilters(username, email, role, enabled);
    }

    @Override
    public Optional<List<User>> findByEnabledIsFalse() {
        return userRepository.findByEnabledIsFalse()
                .map(entities -> entities.stream()
                        .map(mapper::toDomain)
                        .toList());
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public List<User> findAllByRoleId(IDValue roleId) {
        return userRepository.findAllByRoleId(roleId.getId()).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(User user) {
        userRepository.delete(mapper.toEntity(user));
    }
}
