package com.jagt.reader.auth.application.port.input;

import com.jagt.reader.auth.domain.model.CodeSecurity;

public interface UseCodeUseCase {
    void execute(CodeSecurity codeSecurity);
}
