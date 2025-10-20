package com.jagt.reader.auth.infrastructure.input.rest.controller;

import com.jagt.reader.auth.application.port.input.ActivateAccountUseCase;
import com.jagt.reader.auth.application.port.input.GenerateRecoveryCodeUseCase;
import com.jagt.reader.auth.application.port.input.LoginUseCase;
import com.jagt.reader.auth.application.port.input.RecoveryAccountUseCase;
import com.jagt.reader.auth.application.port.input.RefreshSessionUseCase;
import com.jagt.reader.auth.application.port.input.RegisterUserCase;
import com.jagt.reader.auth.application.port.input.ResendActivationCodeUseCase;
import com.jagt.reader.auth.domain.model.Token;
import com.jagt.reader.auth.infrastructure.input.rest.controller.doc.AuthControllerDoc;
import com.jagt.reader.auth.infrastructure.input.rest.mapper.AuthRestMapper;
import com.jagt.reader.auth.infrastructure.input.rest.request.ActivateAccountRequest;
import com.jagt.reader.auth.infrastructure.input.rest.request.LoginRequest;
import com.jagt.reader.auth.infrastructure.input.rest.request.RecoveryCodeRequest;
import com.jagt.reader.auth.infrastructure.input.rest.request.RefreshTokenRequest;
import com.jagt.reader.auth.infrastructure.input.rest.request.RegisterRequest;
import com.jagt.reader.auth.infrastructure.input.rest.request.ResendActivationCodeRequest;
import com.jagt.reader.auth.infrastructure.input.rest.request.ResetPasswordRequest;
import com.jagt.reader.auth.infrastructure.input.rest.response.LoginResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

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
    private final ResendActivationCodeUseCase resendActivationCodeUseCase;

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest request, HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        String clientIp = getClientIp(httpRequest);
        Token responseUseCase = loginUseCase.execute(mapper.toCommand(request, clientIp));

        ResponseCookie cookie = ResponseCookie.from("refreshToken", responseUseCase.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .path("/api/auth/refresh")
                .maxAge(7 * 24 * 60 * 60)
                .sameSite("Strict")
                .build();

        httpResponse.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok(
                mapper.toResponse(responseUseCase)
        );
    }

    @Override
    public ResponseEntity<Void> register(RegisterRequest request) {
        registerUserCase.execute(mapper.toCommand(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> resendActivationCode(ResendActivationCodeRequest request) {
        resendActivationCodeUseCase.execute(mapper.toCommand(request));
        return ResponseEntity.noContent().build();
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
    public ResponseEntity<LoginResponse> refreshToken(RefreshTokenRequest request, HttpServletRequest httpRequest) {
        String clientIp = getClientIp(httpRequest);

        String refreshToken = Arrays.stream(httpRequest.getCookies())
                .filter(c -> "refreshToken".equals(c.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(request.refreshToken());

        RefreshTokenRequest request1 = new RefreshTokenRequest(refreshToken);

        return ResponseEntity.ok(mapper.toResponse(
                refreshSessionUseCase.execute(mapper.toCommand(request1, clientIp))
        ));
    }

    @Override
    public ResponseEntity<Void> logout(HttpServletResponse httpResponse) {
        ResponseCookie deleteCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(true)
                .path("/api/auth/refresh")
                .maxAge(0)
                .build();
        httpResponse.addHeader(HttpHeaders.SET_COOKIE, deleteCookie.toString());
        return ResponseEntity.noContent().build();
    }

    private String getClientIp(HttpServletRequest request) {
        String forwarded = request.getHeader("X-Forwarded-For");
        if (forwarded != null && !forwarded.isBlank()) {
            return forwarded.split(",")[0];
        }
        return request.getRemoteAddr();
    }
}
