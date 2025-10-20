package com.jagt.reader.auth.application.usecase;

import com.jagt.reader.auth.application.port.input.CleanCodesByTypeUseCase;
import com.jagt.reader.auth.domain.model.CodeSecurity;
import com.jagt.reader.auth.domain.model.enums.CodeType;
import com.jagt.reader.auth.domain.port.output.CodeSecurityPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CleanCodesByTypeUseCaseImpl implements CleanCodesByTypeUseCase {
    private final CodeSecurityPersistencePort codeSecurityPersistencePort;

    @Override
    public void execute(CodeType codeType) {
        List<CodeSecurity> expiredOrUsedCodes = codeSecurityPersistencePort.findAllByTypeAndUsedTrueAndExpirationBefore(codeType, LocalDateTime.now());
        if (!expiredOrUsedCodes.isEmpty()) {
            codeSecurityPersistencePort.deleteAll(expiredOrUsedCodes);
        }
    }
}
