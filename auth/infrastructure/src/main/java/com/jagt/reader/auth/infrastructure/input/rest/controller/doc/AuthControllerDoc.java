package com.jagt.reader.auth.infrastructure.input.rest.controller.doc;

import com.jagt.reader.auth.infrastructure.input.rest.request.ActivateAccountRequest;
import com.jagt.reader.auth.infrastructure.input.rest.request.LoginRequest;
import com.jagt.reader.auth.infrastructure.input.rest.request.RefreshTokenRequest;
import com.jagt.reader.auth.infrastructure.input.rest.request.RegisterRequest;
import com.jagt.reader.auth.infrastructure.input.rest.request.RecoveryCodeRequest;
import com.jagt.reader.auth.infrastructure.input.rest.request.ResetPasswordRequest;
import com.jagt.reader.auth.infrastructure.input.rest.response.LoginResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Auth", description = "Operaciones relacionadas con la autenticación dentro del aplicativo")
public interface AuthControllerDoc {
    @PostMapping("/login")
    ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request);

    @PostMapping("/register")
    ResponseEntity<Void> register(@RequestBody RegisterRequest request);

    @PostMapping("/activate")
    ResponseEntity<Void> activateAccount(@RequestBody ActivateAccountRequest request);

    @PostMapping("/recovery/request")
    ResponseEntity<Void> requestRecoveryCode(@RequestBody RecoveryCodeRequest request);

    @PostMapping("/recovery/reset")
    ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordRequest request);

    @PostMapping("/refresh")
    ResponseEntity<LoginResponse> refreshToken(@RequestBody RefreshTokenRequest request);
}
