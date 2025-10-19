package com.jagt.reader.auth.infrastructure.input.rest.controller.doc;

import com.jagt.reader.auth.infrastructure.input.rest.request.ActivateAccountRequest;
import com.jagt.reader.auth.infrastructure.input.rest.request.LoginRequest;
import com.jagt.reader.auth.infrastructure.input.rest.request.RefreshTokenRequest;
import com.jagt.reader.auth.infrastructure.input.rest.request.RegisterRequest;
import com.jagt.reader.auth.infrastructure.input.rest.request.RecoveryCodeRequest;
import com.jagt.reader.auth.infrastructure.input.rest.request.ResendActivationCodeRequest;
import com.jagt.reader.auth.infrastructure.input.rest.request.ResetPasswordRequest;
import com.jagt.reader.auth.infrastructure.input.rest.response.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Auth", description = "Operaciones relacionadas con la autenticación dentro del aplicativo")
public interface AuthControllerDoc {
    @Operation(summary = "Inicio de sesión del usuario al sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inicio de sesión exitoso",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/login")
    ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, HttpServletRequest httpRequest, HttpServletResponse httpResponse);

    @Operation(summary = "Registrar un usuario al sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registro exitoso",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Algún dato ingresado no es válido",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/register")
    ResponseEntity<Void> register(@RequestBody RegisterRequest request);

    @Operation(summary = "Reenviar código de activación de la cuenta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Código enviado exitosamente",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/activation/resend")
    ResponseEntity<Void> resendActivationCode(@RequestBody ResendActivationCodeRequest request);

    @Operation(summary = "Activar cuenta registrada del usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Activación de cuenta exitosa",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Código de activación inválido para el usuario",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/activation")
    ResponseEntity<Void> activateAccount(@RequestBody ActivateAccountRequest request);

    @Operation(summary = "Enviar código para recuperar cuenta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Código enviado exitosamente",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/recovery/request")
    ResponseEntity<Void> requestRecoveryCode(@RequestBody RecoveryCodeRequest request);

    @Operation(summary = "Recuperar cuenta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cuenta recuperada exitosamente",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Código de recuperación inválido",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/recovery/reset")
    ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordRequest request);

    @Operation(summary = "Refrescar token de sesión")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token generado exitosamente",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/refresh")
    @SecurityRequirement(name = "cookieAuth")
    ResponseEntity<LoginResponse> refreshToken(@RequestBody RefreshTokenRequest request, HttpServletRequest httpRequest);

    @Operation(summary = "Cierre de sesión del usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cierre exitoso",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/logout")
    @SecurityRequirement(name = "cookieAuth")
    ResponseEntity<Void> logout(HttpServletResponse httpResponse);
}
