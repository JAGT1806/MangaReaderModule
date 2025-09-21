package com.jagt.reader.auth.application.port.input;

import com.jagt.reader.auth.application.command.ResendActivationCodeCommand;

public interface ResendActivationCodeUseCase {
    void execute(ResendActivationCodeCommand command);
}
