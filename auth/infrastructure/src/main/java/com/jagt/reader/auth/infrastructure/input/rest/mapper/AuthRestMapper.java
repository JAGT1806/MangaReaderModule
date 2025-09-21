package com.jagt.reader.auth.infrastructure.input.rest.mapper;

import com.jagt.reader.auth.application.command.ActivateAccountCommand;
import com.jagt.reader.auth.application.command.GenerateCodeCommand;
import com.jagt.reader.auth.application.command.GenerateRecoveryCodeCommand;
import com.jagt.reader.auth.application.command.LoginCommand;
import com.jagt.reader.auth.application.command.RecoveryAccountCommand;
import com.jagt.reader.auth.application.command.RefreshSessionCommand;
import com.jagt.reader.auth.application.command.RegisterCommand;
import com.jagt.reader.auth.domain.model.Token;
import com.jagt.reader.auth.infrastructure.input.rest.request.ActivateAccountRequest;
import com.jagt.reader.auth.infrastructure.input.rest.request.LoginRequest;
import com.jagt.reader.auth.infrastructure.input.rest.request.RecoveryCodeRequest;
import com.jagt.reader.auth.infrastructure.input.rest.request.RefreshTokenRequest;
import com.jagt.reader.auth.infrastructure.input.rest.request.RegisterRequest;
import com.jagt.reader.auth.infrastructure.input.rest.request.ResetPasswordRequest;
import com.jagt.reader.auth.infrastructure.input.rest.response.LoginResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthRestMapper {
    LoginCommand toCommand(LoginRequest request);

    LoginResponse toResponse(Token model);

    RegisterCommand toCommand(RegisterRequest request);

    ActivateAccountCommand toCommand(ActivateAccountRequest request);

    GenerateRecoveryCodeCommand toCommand(RecoveryCodeRequest request);

    RecoveryAccountCommand toCommand(ResetPasswordRequest request);

    RefreshSessionCommand toCommand(RefreshTokenRequest request);
}
