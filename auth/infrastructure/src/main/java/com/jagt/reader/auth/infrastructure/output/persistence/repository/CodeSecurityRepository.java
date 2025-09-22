package com.jagt.reader.auth.infrastructure.output.persistence.repository;

import com.jagt.reader.auth.infrastructure.output.persistence.entity.CodeSecurityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CodeSecurityRepository extends JpaRepository<CodeSecurityEntity, Long> {
    List<CodeSecurityEntity> findAllByUser_IdAndType(Long userId, String type);

    Optional<CodeSecurityEntity> findByCodeAndUser_Id(String code, Long userId);
}
