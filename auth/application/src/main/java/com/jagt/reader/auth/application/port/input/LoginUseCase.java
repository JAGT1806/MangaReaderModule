package com.jagt.reader.auth.application.port.input;

import com.jagt.reader.auth.application.command.LoginCommand;
import com.jagt.reader.auth.domain.model.Token;

public interface LoginUseCase {
    Token execute(LoginCommand command);
}
