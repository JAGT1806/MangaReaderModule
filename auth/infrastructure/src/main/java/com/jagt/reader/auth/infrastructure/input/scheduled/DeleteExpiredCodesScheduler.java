package com.jagt.reader.auth.infrastructure.input.scheduled;

import com.jagt.reader.auth.application.port.input.CleanCodesByTypeUseCase;
import com.jagt.reader.auth.domain.model.enums.CodeType;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteExpiredCodesScheduler {
    private final CleanCodesByTypeUseCase cleanCodesByTypeUseCase;

    @Scheduled(cron = "0 0 1 * * *") // 01:00 AM
    void cleanActivationCodes() {
        cleanCodesByTypeUseCase.execute(CodeType.ACTIVATION);
    }

    @Scheduled(cron = "0 0 * * * *") // per hour
    void cleanRecoveryCodes() {
        cleanCodesByTypeUseCase.execute(CodeType.RECOVERING);
    }
}
