package com.jagt.reader.auth.infrastructure.input.rest.controller.doc;

import com.jagt.reader.auth.infrastructure.input.rest.request.ActivateAccountRequest;
import com.jagt.reader.auth.infrastructure.input.rest.request.LoginRequest;
import com.jagt.reader.auth.infrastructure.input.rest.request.RefreshTokenRequest;
import com.jagt.reader.auth.infrastructure.input.rest.request.RegisterRequest;
import com.jagt.reader.auth.infrastructure.input.rest.request.RecoveryCodeRequest;
import com.jagt.reader.auth.infrastructure.input.rest.request.ResendActivationCodeRequest;
import com.jagt.reader.auth.infrastructure.input.rest.request.ResetPasswordRequest;
import com.jagt.reader.auth.infrastructure.input.rest.response.LoginResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Auth", description = "Operaciones relacionadas con la autenticación dentro del aplicativo")
public interface AuthControllerDoc {
    @PostMapping("/login")
    ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, HttpServletRequest httpRequest, HttpServletResponse httpResponse);

    @PostMapping("/register")
    ResponseEntity<Void> register(@RequestBody RegisterRequest request);

    @PostMapping("/activation/resend")
    ResponseEntity<Void> resendActivationCode(@RequestBody ResendActivationCodeRequest request);

    @PostMapping("/activation")
    ResponseEntity<Void> activateAccount(@RequestBody ActivateAccountRequest request);

    @PostMapping("/recovery/request")
    ResponseEntity<Void> requestRecoveryCode(@RequestBody RecoveryCodeRequest request);

    @PostMapping("/recovery/reset")
    ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordRequest request);

    @PostMapping("/refresh")
    @SecurityRequirement(name = "cookieAuth")
    ResponseEntity<LoginResponse> refreshToken(@RequestBody RefreshTokenRequest request, HttpServletRequest httpRequest);

    @PostMapping("/logout")
    ResponseEntity<Void> logout(HttpServletResponse httpResponse);
}
