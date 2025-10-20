package com.jagt.reader.user.application.usecase;

import com.jagt.reader.user.application.port.input.CleanInactiveUsersUseCase;
import com.jagt.reader.user.domain.port.output.UserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CleanInactiveUsersUseCaseImpl implements CleanInactiveUsersUseCase {
    private final UserPersistencePort userPersistencePort;

    @Override
    public void execute() {
        userPersistencePort.findByEnabledIsFalse().orElse(Collections.emptyList())
                .stream()
                .filter(user -> Duration.between(user.getAuditTimestamps().createdAt(), LocalDateTime.now()).toDays() > 10)
                .forEach(userPersistencePort::delete);
    }
}
