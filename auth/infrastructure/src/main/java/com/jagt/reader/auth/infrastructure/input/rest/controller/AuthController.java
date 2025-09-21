package com.jagt.reader.auth.infrastructure.input.rest.controller;

import com.jagt.reader.auth.application.port.input.ActivateAccountUseCase;
import com.jagt.reader.auth.application.port.input.GenerateRecoveryCodeUseCase;
import com.jagt.reader.auth.application.port.input.LoginUseCase;
import com.jagt.reader.auth.application.port.input.RecoveryAccountUseCase;
import com.jagt.reader.auth.application.port.input.RefreshSessionUseCase;
import com.jagt.reader.auth.application.port.input.RegisterUserCase;
import com.jagt.reader.auth.infrastructure.input.rest.controller.doc.AuthControllerDoc;
import com.jagt.reader.auth.infrastructure.input.rest.mapper.AuthRestMapper;
import com.jagt.reader.auth.infrastructure.input.rest.request.ActivateAccountRequest;
import com.jagt.reader.auth.infrastructure.input.rest.request.LoginRequest;
import com.jagt.reader.auth.infrastructure.input.rest.request.RecoveryCodeRequest;
import com.jagt.reader.auth.infrastructure.input.rest.request.RefreshTokenRequest;
import com.jagt.reader.auth.infrastructure.input.rest.request.RegisterRequest;
import com.jagt.reader.auth.infrastructure.input.rest.request.ResetPasswordRequest;
import com.jagt.reader.auth.infrastructure.input.rest.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/")
public class AuthController implements AuthControllerDoc {
    private final AuthRestMapper mapper;
    private final LoginUseCase loginUseCase;
    private final RegisterUserCase registerUserCase;
    private final ActivateAccountUseCase activateAccountUseCase;
    private final GenerateRecoveryCodeUseCase generateRecoveryCodeUseCase;
    private final RecoveryAccountUseCase recoveryAccountUseCase;
    private final RefreshSessionUseCase refreshSessionUseCase;

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest request) {
        return ResponseEntity.ok(
                mapper.toResponse(loginUseCase.execute(mapper.toCommand(request)))
        );
    }

    @Override
    public ResponseEntity<Void> register(RegisterRequest request) {
        registerUserCase.execute(mapper.toCommand(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> activateAccount(ActivateAccountRequest request) {
        activateAccountUseCase.execute(mapper.toCommand(request));
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> requestRecoveryCode(RecoveryCodeRequest request) {
        generateRecoveryCodeUseCase.execute(mapper.toCommand(request));
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> resetPassword(ResetPasswordRequest request) {
        recoveryAccountUseCase.execute(mapper.toCommand(request));
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<LoginResponse> refreshToken(RefreshTokenRequest request) {
        return ResponseEntity.ok(mapper.toResponse(
                refreshSessionUseCase.execute(mapper.toCommand(request))
        ));
    }
}
