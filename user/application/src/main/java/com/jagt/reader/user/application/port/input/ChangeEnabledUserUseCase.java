package com.jagt.reader.user.application.port.input;

import com.jagt.reader.user.application.command.ChangeEnabledUseCommand;

public interface ChangeEnabledUserUseCase {
    void execute(ChangeEnabledUseCommand command);
}
