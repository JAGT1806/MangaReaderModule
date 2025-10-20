package com.jagt.reader.auth.application.usecase;

import com.jagt.reader.auth.application.command.ActivateAccountCommand;
import com.jagt.reader.auth.application.port.input.ActivateAccountUseCase;
import com.jagt.reader.auth.application.port.input.GetCodeUseCase;
import com.jagt.reader.auth.application.port.input.ValidateCodeUseCase;
import com.jagt.reader.auth.domain.model.CodeSecurity;
import com.jagt.reader.auth.domain.port.output.CodeSecurityPersistencePort;
import com.jagt.reader.shared.common.domain.model.value.IDValue;
import com.jagt.reader.shared.i18n.domain.service.MessageProvider;
import com.jagt.reader.user.application.command.ChangeEnabledUseCommand;
import com.jagt.reader.user.application.port.input.ChangeEnabledUserUseCase;
import com.jagt.reader.user.application.port.input.GetUserUseCase;
import com.jagt.reader.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivateAccountUseCaseImpl implements ActivateAccountUseCase {
    private final GetUserUseCase getUserUseCase;
    private final GetCodeUseCase getCodeUseCase;
    private final ValidateCodeUseCase validateCodeUseCase;
    private final ChangeEnabledUserUseCase changeEnabledUserUseCase;
    private final CodeSecurityPersistencePort persistencePort;
    private final MessageProvider messageProvider;

    @Override
    public void execute(ActivateAccountCommand command) {
        User user = getUserUseCase.execute(command.email());
        CodeSecurity codeSecurity = getCodeUseCase.execute(command.code(), user.getId().getId());
        validateCodeUseCase.execute(codeSecurity);

        changeEnabledUserUseCase.execute(new ChangeEnabledUseCommand(
                IDValue.builder().id(user.getId().getId()).build(),
                true
        ));
        codeSecurity.setUsed(true);

        persistencePort.save(codeSecurity);
    }
}
