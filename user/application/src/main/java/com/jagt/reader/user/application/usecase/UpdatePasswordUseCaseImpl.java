package com.jagt.reader.user.application.usecase;

import com.jagt.reader.shared.i18n.domain.service.MessageProvider;
import com.jagt.reader.user.application.command.ChangePasswordAuthCommand;
import com.jagt.reader.user.application.command.UpdatePasswordCommand;
import com.jagt.reader.user.application.port.input.GetUserUseCase;
import com.jagt.reader.user.application.port.input.UpdatePasswordUseCase;
import com.jagt.reader.user.domain.model.User;
import com.jagt.reader.user.domain.port.output.PasswordService;
import com.jagt.reader.user.domain.port.output.UserPersistencePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdatePasswordUseCaseImpl implements UpdatePasswordUseCase {
    private final GetUserUseCase getUserUseCase;
    private final PasswordService passwordService;
    private final UserPersistencePort persistencePort;
    private final MessageProvider messageProvider;

    @Override
    public void execute(ChangePasswordAuthCommand command) {
        User user = getUserUseCase.execute(command.userID().getId());

        user.getUserValue().setPassword(passwordService.encode(command.newPassword()));
        user.setAuditTimestamps(user.getAuditTimestamps().updated());

        persistencePort.save(user);
    }

    @Override
    public void execute(UpdatePasswordCommand command) {
        User user = getUserUseCase.execute(command.userId().getId());
        if (!passwordService.matches(command.password(), user.getUserValue().getPassword())) {
            throw new IllegalArgumentException(messageProvider.getMessage("user.password.incorrect"));
        }

        if (passwordService.matches(command.newPassword(), user.getUserValue().getPassword())) {
            throw new IllegalArgumentException(messageProvider.getMessage("user.password.equals"));
        }

        user.getUserValue().setPassword(passwordService.encode(command.newPassword()));
        user.setAuditTimestamps(user.getAuditTimestamps().updated());
        persistencePort.save(user);
    }
}
