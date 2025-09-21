package com.jagt.reader.user.application.usecase;

import com.jagt.reader.shared.common.domain.model.value.AuditTimestampsValue;
import com.jagt.reader.user.application.command.ChangeEnabledUseCommand;
import com.jagt.reader.user.application.port.input.ChangeEnabledUserUseCase;
import com.jagt.reader.user.domain.model.User;
import com.jagt.reader.user.domain.port.output.UserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChangeEnabledUserUseCaseImpl implements ChangeEnabledUserUseCase {
    private final GetUserUseCaseImpl getUserUseCase;
    private final UserPersistencePort persistencePort;

    @Override
    public void execute(ChangeEnabledUseCommand command) {
        User user = getUserUseCase.execute(command.userId().getId());

        user.setEnabled(command.enabled());
        user.setAuditTimestamps(user.getAuditTimestamps().updated());

        persistencePort.save(user);
    }
}
