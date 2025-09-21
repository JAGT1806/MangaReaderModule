package com.jagt.reader.user.application.usecase;

import com.jagt.reader.user.application.command.ChangePasswordAuthCommand;
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

    @Override
    public void execute(ChangePasswordAuthCommand command) {
        User user = getUserUseCase.execute(command.userID().getId());

        user.getUserValue().setPassword(passwordService.encode(command.newPassword()));
        user.setAuditTimestamps(user.getAuditTimestamps().updated());

        persistencePort.save(user);
    }
}
