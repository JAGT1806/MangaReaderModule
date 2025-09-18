package com.jagt.reader.auth.application.usecase;

import com.jagt.reader.auth.application.command.GenerateCodeCommand;
import com.jagt.reader.auth.application.command.RegisterCommand;
import com.jagt.reader.auth.application.port.input.GenerateCodeUseCase;
import com.jagt.reader.auth.application.port.input.RegisterUserCase;
import com.jagt.reader.auth.domain.model.enums.CodeType;
import com.jagt.reader.shared.common.domain.model.value.NameValue;
import com.jagt.reader.user.application.command.CreateUserCommand;
import com.jagt.reader.user.application.port.input.CreateUserUseCase;
import com.jagt.reader.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterUserCaseImpl implements RegisterUserCase {
    private final CreateUserUseCase createUserUseCase;
    private final GenerateCodeUseCase generateCodeUseCase;

    @Override
    public void execute(RegisterCommand command) {
        User userCreated = createUserUseCase.execute(new CreateUserCommand(
                NameValue.builder().name(command.username()).build(),
                NameValue.builder().name(command.email()).build(),
                command.password()
        ));

        generateCodeUseCase.execute(new GenerateCodeCommand(userCreated, CodeType.ACTIVATION));
    }
}
