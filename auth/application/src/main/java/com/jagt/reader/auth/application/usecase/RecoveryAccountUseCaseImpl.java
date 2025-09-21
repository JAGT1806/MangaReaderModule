package com.jagt.reader.auth.application.usecase;

import com.jagt.reader.auth.application.command.RecoveryAccountCommand;
import com.jagt.reader.auth.application.port.input.GetCodeUseCase;
import com.jagt.reader.auth.application.port.input.RecoveryAccountUseCase;
import com.jagt.reader.auth.application.port.input.ValidateCodeUseCase;
import com.jagt.reader.auth.domain.model.CodeSecurity;
import com.jagt.reader.shared.common.domain.model.value.IDValue;
import com.jagt.reader.user.application.command.ChangePasswordAuthCommand;
import com.jagt.reader.user.application.port.input.GetUserUseCase;
import com.jagt.reader.user.application.port.input.UpdatePasswordUseCase;
import com.jagt.reader.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecoveryAccountUseCaseImpl implements RecoveryAccountUseCase {
    private final GetUserUseCase getUserUseCase;
    private final GetCodeUseCase getCodeUseCase;
    private final ValidateCodeUseCase validateCodeUseCase;
    private final UpdatePasswordUseCase updatePasswordUseCase;

    @Override
    public void execute(RecoveryAccountCommand command) {
        User user = getUserUseCase.execute(command.email());
        CodeSecurity codeSecurity = getCodeUseCase.execute(
                command.code(), user.getId().getId()
        );
        validateCodeUseCase.execute(codeSecurity);

        updatePasswordUseCase.execute(new ChangePasswordAuthCommand(
                IDValue.builder().id(user.getId().getId()).build(),
                command.newPassword()
        ));
    }
}
