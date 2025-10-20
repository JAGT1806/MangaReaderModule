package com.jagt.reader.auth.application.usecase;

import com.jagt.reader.auth.application.command.GenerateCodeCommand;
import com.jagt.reader.auth.application.command.GenerateRecoveryCodeCommand;
import com.jagt.reader.auth.application.port.input.GenerateCodeUseCase;
import com.jagt.reader.auth.application.port.input.GenerateRecoveryCodeUseCase;
import com.jagt.reader.auth.domain.model.enums.CodeType;
import com.jagt.reader.shared.i18n.domain.service.MessageProvider;
import com.jagt.reader.user.application.port.input.GetUserUseCase;
import com.jagt.reader.user.domain.exception.UserNotEnabledException;
import com.jagt.reader.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenerateRecoveryCodeUseCaseImpl implements GenerateRecoveryCodeUseCase {
    private final GetUserUseCase getUserUseCase;
    private final MessageProvider messageProvider;
    private final GenerateCodeUseCase generateCodeUseCase;

    @Override
    public void execute(GenerateRecoveryCodeCommand command) {
        User user = getUserUseCase.execute(command.email());

        if (!user.isEnabled())
            throw new UserNotEnabledException(messageProvider.getMessage("user.not.enabled.code"));

        generateCodeUseCase.execute(new GenerateCodeCommand(
                user, CodeType.RECOVERING
        ));
    }
}
