package com.jagt.reader.auth.application.port.input;

import com.jagt.reader.auth.application.command.GenerateCodeCommand;

public interface GenerateCodeUseCase {
    void execute(GenerateCodeCommand command);
}
