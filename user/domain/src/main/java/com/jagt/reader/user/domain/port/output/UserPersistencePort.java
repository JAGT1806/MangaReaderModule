package com.jagt.reader.user.domain.port.output;

import com.jagt.reader.shared.common.domain.model.value.IDValue;
import com.jagt.reader.user.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserPersistencePort {
    User save(User user);

    void deleteById(Long id);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    List<User> findByFilters(String username, String email, String role, Boolean enabled, int offset, int limit);

    long countByFilters(String username, String email, String role, Boolean enabled);

    Optional<List<User>> findByEnabledIsFalse();

    boolean existsByEmail(String email);

    List<User> findAllByRoleId(IDValue roleId);
}
