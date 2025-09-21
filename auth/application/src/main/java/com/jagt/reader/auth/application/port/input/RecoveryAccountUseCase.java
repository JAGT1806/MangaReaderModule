package com.jagt.reader.auth.application.port.input;

import com.jagt.reader.auth.application.command.RecoveryAccountCommand;

public interface RecoveryAccountUseCase {
    void execute(RecoveryAccountCommand command);
}
