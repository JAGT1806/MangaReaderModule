package com.jagt.reader.auth.application.usecase;

import com.jagt.reader.auth.application.command.LoginCommand;
import com.jagt.reader.auth.application.mapper.AuthApplicationMapper;
import com.jagt.reader.auth.application.port.input.LoginUseCase;
import com.jagt.reader.auth.domain.model.Token;
import com.jagt.reader.shared.i18n.domain.service.MessageProvider;
import com.jagt.reader.shared.security.domain.model.SecurityUser;
import com.jagt.reader.shared.security.domain.port.output.AuthenticationManagerPort;
import com.jagt.reader.shared.security.domain.port.output.LoadUserDetailsPort;
import com.jagt.reader.shared.security.domain.port.output.TokenProviderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class LoginUseCaseImpl implements LoginUseCase {
    private final AuthenticationManagerPort authenticationManager;
    private final LoadUserDetailsPort loadUserDetailsPort;
    private final TokenProviderPort tokenProvider;
    private final MessageProvider messageProvider;
    private final AuthApplicationMapper mapper;

    @Override
    public Token execute(LoginCommand command) {
        SecurityUser securityUser = loadUserDetailsPort.execute(command.email());

        if (!securityUser.isEnabled())
            throw new IllegalStateException(messageProvider.getMessage("user.not.enabled"));

        authenticationManager.authenticate(command.email(), command.password());

        Map<String, Object> claims = Map.of("roles", securityUser.getRoles(),
                "userId", securityUser.getId());

        String accessToken = tokenProvider.generateAccessToken(command.email().toLowerCase(), claims);
        String refreshToken = tokenProvider.generateRefreshToken(command.email().toLowerCase(), Map.of());

        return mapper.toDomain("Bearer", accessToken, refreshToken);
    }
}
