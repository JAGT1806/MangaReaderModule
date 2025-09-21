package com.jagt.reader.user.application.port.input;

import com.jagt.reader.user.application.command.ChangePasswordAuthCommand;

public interface UpdatePasswordUseCase {
    void execute(ChangePasswordAuthCommand command);
}
