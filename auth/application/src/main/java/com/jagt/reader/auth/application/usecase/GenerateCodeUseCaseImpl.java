package com.jagt.reader.auth.application.usecase;

import com.jagt.reader.auth.application.command.GenerateCodeCommand;
import com.jagt.reader.auth.application.port.input.GenerateCodeUseCase;
import com.jagt.reader.auth.domain.model.CodeSecurity;
import com.jagt.reader.auth.domain.model.enums.CodeType;
import com.jagt.reader.auth.domain.port.output.CodeSecurityPersistencePort;
import com.jagt.reader.auth.domain.port.output.EmailSenderPort;
import com.jagt.reader.shared.common.domain.model.value.AuditTimestampsValue;
import com.jagt.reader.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GenerateCodeUseCaseImpl implements GenerateCodeUseCase {
    private final CodeSecurityPersistencePort codeSecurityPersistencePort;
    private final EmailSenderPort emailSenderPort;

    private static final SecureRandom RANDOM = new SecureRandom();

    @Value("${app.security.token.activation.expiration}")
    private int activationExpirationHours;
    @Value("${app.security.token.recovery.expiration}")
    private int recoveryExpirationHours;

    @Override
    public void execute(GenerateCodeCommand command) {
        invalidateExistingCodes(command.user(), command.codeType());
        String code = generateCode(command.codeType());
        int expirationHours = getExpirationHours(command.codeType());

        CodeSecurity codeSecurity = CodeSecurity.builder()
                .user(command.user())
                .code(code)
                .type(command.codeType())
                .expiration(LocalDateTime.now().plusHours(expirationHours))
                .used(false)
                .audit(AuditTimestampsValue.now())
                .build();

        codeSecurityPersistencePort.save(codeSecurity);

        emailSenderPort.sendCode(command.user().getUserValue().getEmail(), code, command.codeType(), codeSecurity.getExpiration());
    }

    private void invalidateExistingCodes(User user, CodeType codeType) {
        List<CodeSecurity> existingCodes = codeSecurityPersistencePort.findByUserIdAndCodeType(user.getId().getId(), codeType.name());
        existingCodes.forEach(code -> code.setUsed(true));
        codeSecurityPersistencePort.saveAll(existingCodes);
    }


    private String generateCode(CodeType codeType) {
        String prefix = codeType.getPrefix();
        int length = codeType.getLength() - prefix.length();

        int min = (int) Math.pow(10, length - 1);
        int max = (int) Math.pow(10, length) - 1;

        int number = RANDOM.nextInt((max - min) + 1) + min;
        return prefix + String.format("%0" + length + "d", number);
    }

    private int getExpirationHours(CodeType codeType) {
        return switch (codeType) {
            case ACTIVATION -> activationExpirationHours;
            case RECOVERING -> recoveryExpirationHours;
        };
    }
}
