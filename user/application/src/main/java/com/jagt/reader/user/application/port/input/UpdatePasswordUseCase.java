package com.jagt.reader.user.application.port.input;

import com.jagt.reader.user.application.command.ChangePasswordAuthCommand;
import com.jagt.reader.user.application.command.UpdatePasswordCommand;

public interface UpdatePasswordUseCase {
    void execute(ChangePasswordAuthCommand command);

    void execute(UpdatePasswordCommand command);
}
