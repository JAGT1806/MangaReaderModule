package com.jagt.reader.auth.application.usecase;

import com.jagt.reader.auth.application.port.input.UseCodeUseCase;
import com.jagt.reader.auth.domain.model.CodeSecurity;
import com.jagt.reader.auth.domain.port.output.CodeSecurityPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UseCodeUseCaseImpl implements UseCodeUseCase {
    private final CodeSecurityPersistencePort persistencePort;

    @Override
    public void execute(CodeSecurity codeSecurity) {
        codeSecurity.setUsed(true);
        persistencePort.save(codeSecurity);
    }
}
