package com.jagt.reader.auth.application.usecase;

import com.jagt.reader.auth.application.command.GenerateCodeCommand;
import com.jagt.reader.auth.application.command.ResendActivationCodeCommand;
import com.jagt.reader.auth.application.port.input.GenerateCodeUseCase;
import com.jagt.reader.auth.application.port.input.ResendActivationCodeUseCase;
import com.jagt.reader.auth.domain.model.enums.CodeType;
import com.jagt.reader.shared.i18n.domain.service.MessageProvider;
import com.jagt.reader.user.application.port.input.GetUserUseCase;
import com.jagt.reader.user.domain.exception.UserEnabledException;
import com.jagt.reader.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResendActivationCodeUseCaseImpl implements ResendActivationCodeUseCase {
    private final GetUserUseCase getUserUseCase;
    private final MessageProvider messageProvider;
    private final GenerateCodeUseCase generateCodeUseCase;

    @Override
    public void execute(ResendActivationCodeCommand command) {
        User user = getUserUseCase.execute(command.email());

        if (user.isEnabled())
            throw new UserEnabledException(messageProvider.getMessage("user.enabled.code"));

        generateCodeUseCase.execute(new GenerateCodeCommand(
                user, CodeType.ACTIVATION
        ));
    }
}
