package com.jagt.reader.user.infrastructure.input.scheduled;

import com.jagt.reader.user.application.port.input.CleanInactiveUsersUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteInactiveUserScheduled {
    private final CleanInactiveUsersUseCase cleanInactiveUsersUseCase;

    @Scheduled(cron = "0 0 0 * * *") // 00:00 AM
    void execute() {
        cleanInactiveUsersUseCase.execute();
    }
}
