package com.jagt.reader.auth.application.port.input;

import com.jagt.reader.auth.application.command.ActivateAccountCommand;

public interface ActivateAccountUseCase {
    void execute(ActivateAccountCommand command);
}
