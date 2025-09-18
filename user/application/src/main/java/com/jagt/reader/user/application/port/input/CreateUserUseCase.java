package com.jagt.reader.user.application.port.input;


import com.jagt.reader.user.application.command.CreateUserCommand;
import com.jagt.reader.user.domain.model.User;

public interface CreateUserUseCase {
    User execute(CreateUserCommand command);
}
