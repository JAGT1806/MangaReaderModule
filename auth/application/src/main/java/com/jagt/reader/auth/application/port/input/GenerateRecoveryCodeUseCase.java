package com.jagt.reader.auth.application.port.input;

import com.jagt.reader.auth.application.command.GenerateRecoveryCodeCommand;

public interface GenerateRecoveryCodeUseCase {
    void execute(GenerateRecoveryCodeCommand command);
}
