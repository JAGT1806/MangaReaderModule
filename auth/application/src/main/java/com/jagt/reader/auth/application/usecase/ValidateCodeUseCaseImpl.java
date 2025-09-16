package com.jagt.reader.auth.application.usecase;

import com.jagt.reader.auth.application.port.input.ValidateCodeUseCase;
import com.jagt.reader.auth.domain.model.CodeSecurity;
import com.jagt.reader.auth.domain.port.output.CodeSecurityPersistencePort;
import com.jagt.reader.shared.i18n.domain.service.MessageProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ValidateCodeUseCaseImpl implements ValidateCodeUseCase {
    private final MessageProvider messageProvider;
    private final CodeSecurityPersistencePort persistencePort;

    @Override
    public void execute(CodeSecurity codeSecurity) {
        if (codeSecurity == null || codeSecurity.getCode() == null || codeSecurity.getCode().trim().isEmpty())
            throw new IllegalArgumentException(messageProvider.getMessage("code.null"));

        if (codeSecurity.isUsed()) {
            deleteCode(codeSecurity);
            throw new IllegalArgumentException(messageProvider.getMessage("code.used"));
        }

        if (codeSecurity.getCode().length() != codeSecurity.getType().getLength())
            throw new IllegalArgumentException(messageProvider.getMessage("code.length.invalid"));

        if (LocalDateTime.now().isAfter(codeSecurity.getExpiration())) {
            deleteCode(codeSecurity);
            throw new IllegalArgumentException(messageProvider.getMessage("code.expired"));
        }
    }

    private void deleteCode(CodeSecurity codeSecurity) {
        persistencePort.delete(codeSecurity);
    }
}
