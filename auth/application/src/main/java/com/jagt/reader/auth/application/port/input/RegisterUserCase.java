package com.jagt.reader.auth.application.port.input;

import com.jagt.reader.auth.application.command.RegisterCommand;

public interface RegisterUserCase {
    void execute(RegisterCommand command);
}
