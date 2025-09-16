package com.jagt.reader.auth.application.port.input;

import com.jagt.reader.auth.application.command.RefreshSessionCommand;
import com.jagt.reader.auth.domain.model.Token;

public interface RefreshSessionUseCase {
    Token execute(RefreshSessionCommand command);
}
