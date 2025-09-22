package com.jagt.reader.auth.application.usecase;

import com.jagt.reader.auth.application.command.RefreshSessionCommand;
import com.jagt.reader.auth.application.mapper.AuthApplicationMapper;
import com.jagt.reader.auth.application.port.input.RefreshSessionUseCase;
import com.jagt.reader.auth.domain.model.Token;
import com.jagt.reader.shared.i18n.domain.service.MessageProvider;
import com.jagt.reader.shared.security.domain.model.SecurityUser;
import com.jagt.reader.shared.security.domain.port.output.LoadUserDetailsPort;
import com.jagt.reader.shared.security.domain.port.output.TokenProviderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class RefreshSessionUseCaseImpl implements RefreshSessionUseCase {
    private final TokenProviderPort tokenProvider;
    private final MessageProvider messageProvider;
    private final LoadUserDetailsPort loadUserDetailsPort;
    private final AuthApplicationMapper mapper;

    @Override
    public Token execute(RefreshSessionCommand command) {
        String refreshToken = command.refreshToken();

        if (!tokenProvider.validateToken(refreshToken))
            throw new IllegalArgumentException(messageProvider.getMessage("token.refresh.invalid"));

        String userIp = tokenProvider.extractIp(refreshToken);

        if (!userIp.equals(command.ip()))
            throw new IllegalArgumentException(messageProvider.getMessage("token.refresh.invalid"));

        String username = tokenProvider.extractUsername(refreshToken);
        SecurityUser securityUser = loadUserDetailsPort.execute(username);

        Map<String, Object> claims = Map.of("roles", securityUser.getRoles(),
                "userId", securityUser.getId(),
                "ip", userIp);

        String newAccessToken = tokenProvider.generateAccessToken(username, claims);

        return mapper.toDomain("Bearer", newAccessToken, refreshToken);
    }
}
