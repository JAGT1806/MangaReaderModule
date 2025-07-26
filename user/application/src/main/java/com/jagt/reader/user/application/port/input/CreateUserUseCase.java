package com.jagt.reader.user.application.port.input;


import com.jagt.reader.user.application.command.CreateUserCommand;

public interface CreateUserUseCase {
    void execute(CreateUserCommand command);
}
