package com.jagt.reader.auth.infrastructure.output.persistence.adapter;

import com.jagt.reader.auth.domain.model.CodeSecurity;
import com.jagt.reader.auth.domain.port.output.CodeSecurityPersistencePort;
import com.jagt.reader.auth.infrastructure.output.persistence.mapper.CodeSecurityPersistenceMapper;
import com.jagt.reader.auth.infrastructure.output.persistence.repository.CodeSecurityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CodeSecurityPersistenceAdapter implements CodeSecurityPersistencePort {
    private final CodeSecurityRepository repository;
    private final CodeSecurityPersistenceMapper mapper;

    @Override
    public List<CodeSecurity> findByUserIdAndCodeType(Long userId, String codeType) {
        return repository.findAllByUser_IdAndType(userId, codeType).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deleteAll(List<CodeSecurity> existingCodes) {
        repository.deleteAll(existingCodes.stream()
                .map(mapper::toEntity)
                .toList());
    }

    @Override
    public CodeSecurity save(CodeSecurity codeSecurity) {
        return mapper.toDomain(
                repository.save(mapper.toEntity(codeSecurity))
        );
    }

    @Override
    public void delete(CodeSecurity codeSecurity) {
        repository.delete(mapper.toEntity(codeSecurity));
    }

    @Override
    public Optional<CodeSecurity> findByCodeAndUserId(String code, Long userId) {
        return repository.findByCodeAndUser_Id(code, userId);
    }

    @Override
    public void saveAll(List<CodeSecurity> existingCodes) {
        repository.saveAll(existingCodes.stream()
                .map(mapper::toEntity)
                .toList());
    }
}
