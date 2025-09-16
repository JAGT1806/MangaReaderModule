package com.jagt.reader.auth.application.usecase;

import com.jagt.reader.auth.application.port.input.GetCodeUseCase;
import com.jagt.reader.auth.domain.model.CodeSecurity;
import com.jagt.reader.auth.domain.port.output.CodeSecurityPersistencePort;
import com.jagt.reader.shared.i18n.domain.service.MessageProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCodeUseCaseImpl implements GetCodeUseCase {
    private final CodeSecurityPersistencePort persistencePort;
    private final MessageProvider messageProvider;

    @Override
    public CodeSecurity execute(String code, Long userId) {
        return persistencePort.findByCodeAndUserId(code, userId)
                .orElseThrow(() -> new IllegalArgumentException(messageProvider.getMessage("code.not.found")));
    }
}
