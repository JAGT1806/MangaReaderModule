package com.jagt.reader.auth.domain.port.output;

import com.jagt.reader.auth.domain.model.CodeSecurity;
import com.jagt.reader.auth.domain.model.enums.CodeType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CodeSecurityPersistencePort {
    List<CodeSecurity> findByUserIdAndCodeType(Long userId, String codeType);

    void deleteAll(List<CodeSecurity> existingCodes);

    CodeSecurity save(CodeSecurity codeSecurity);

    void delete(CodeSecurity codeSecurity);

    Optional<CodeSecurity> findByCodeAndUserId(String code, Long userId);

    void saveAll(List<CodeSecurity> existingCodes);

    List<CodeSecurity> findAllByTypeAndUsedTrueAndExpirationBefore(CodeType codeType, LocalDateTime today);
}
