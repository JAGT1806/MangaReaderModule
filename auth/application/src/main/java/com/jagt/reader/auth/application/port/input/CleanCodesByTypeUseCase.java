package com.jagt.reader.auth.application.port.input;

import com.jagt.reader.auth.domain.model.enums.CodeType;

public interface CleanCodesByTypeUseCase {
    void execute(CodeType codeType);
}
