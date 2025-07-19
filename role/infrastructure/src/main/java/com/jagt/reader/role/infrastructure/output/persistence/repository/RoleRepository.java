package com.jagt.reader.role.infrastructure.output.persistence.repository;

import com.jagt.reader.role.infrastructure.output.persistence.entity.RoleEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    List<RoleEntity> findByNameContaining(String name, Pageable pageable);

    Optional<RoleEntity> findByName(String name);

    boolean existsByName(String name);

    long countByNameContaining(String name);
}
