package com.jagt.reader.user.infrastructure.output.persistence.repository;

import com.jagt.reader.user.domain.model.User;
import com.jagt.reader.user.infrastructure.output.persistence.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT DISTINCT u FROM UserEntity u LEFT JOIN u.roles r WHERE " +
            "(:username IS NULL OR u.username LIKE %:username%) AND " +
            "(:email IS NULL OR u.email LIKE %:email%) AND " +
            "(:role IS NULL OR r.name LIKE %:rol%) AND " +
            "(:enabled IS NULL OR u.enabled = :enabled)")
    List<UserEntity> findByFilters(@Param("username") String username,
                              @Param("email") String email,
                              @Param("role") String role,
                              @Param("enabled") Boolean enabled,
                              Pageable pageable);

    @Query("SELECT COUNT(DISTINCT u) FROM UserEntity u LEFT JOIN u.roles r WHERE " +
            "(:username IS NULL OR u.username LIKE %:username%) AND " +
            "(:email IS NULL OR u.email LIKE %:email%) AND " +
            "(:role IS NULL OR r.name LIKE %:rol%) AND " +
            "(:enabled IS NULL OR u.enabled = :enabled)")
    Long countByFilters(@Param("username") String username,
                        @Param("email") String email,
                        @Param("role") String role,
                        @Param("enabled") Boolean enabled);

    Optional<List<UserEntity>> findByEnabledIsFalse();

    @Query("SELECT u FROM UserEntity u LEFT JOIN u.roles r WHERE r.id = :roleId")
    List<UserEntity> findAllByRoleId(Long roleId);
}
