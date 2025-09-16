package com.jagt.reader.auth.application.usecase;

import com.jagt.reader.auth.application.port.input.GetCodeUseCase;
import com.jagt.reader.auth.application.port.input.GetUserByCodeUseCase;
import com.jagt.reader.auth.application.port.input.ValidateCodeUseCase;
import com.jagt.reader.auth.domain.model.CodeSecurity;
import com.jagt.reader.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUserByCodeUseCaseImpl implements GetUserByCodeUseCase {
    private final GetCodeUseCase getCodeUseCase;
    private final ValidateCodeUseCase validateCodeUseCase;

    @Override
    public User execute(String code, Long userId) {
        CodeSecurity codeSecurity = getCodeUseCase.execute(code, userId);
        validateCodeUseCase.execute(codeSecurity);
        return codeSecurity.getUser();
    }
}
