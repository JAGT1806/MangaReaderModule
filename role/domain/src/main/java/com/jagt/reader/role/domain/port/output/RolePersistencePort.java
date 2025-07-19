package com.jagt.reader.role.domain.port.output;

import com.jagt.reader.role.domain.model.Role;

import java.util.List;
import java.util.Optional;

public interface RolePersistencePort {
    List<Role> findAll(int offset, int limit);
    List<Role> findByFilter(String name, int offset, int limit);
    Optional<Role> findById(Long id);
    Optional<Role> findByName(String name);
    boolean existByName(String name);
    Role save(Role role);
    void delete(Long id);
    long count();
    long countByFilter(String name);
}
