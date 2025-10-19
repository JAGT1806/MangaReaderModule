package com.jagt.reader.user.infrastructure.input.rest.controller.doc;

import com.jagt.reader.shared.common.infrastructure.input.web.response.ErrorResponse;
import com.jagt.reader.user.infrastructure.input.rest.request.UpdatePasswordRequest;
import com.jagt.reader.user.infrastructure.input.rest.response.UserListResponse;
import com.jagt.reader.user.infrastructure.input.rest.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "User", description = "Operaciones relacionadas con los usuarios")
@SecurityRequirement(name = "bearerAuth")
public interface UserControllerDoc {

    @Operation(summary = "Obtener usuarios", description = "Obtener una lista paginada de usuarios del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping
    UserListResponse getUsers(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @Parameter(in = ParameterIn.QUERY, description = "Filtrar por rol",
                    allowEmptyValue = true,
                    schema = @Schema(allowableValues = { "ADMIN", "USER" }))
            @RequestParam(required = false) String role,
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "12") int limit,
            @RequestParam(required = false) Boolean enabled
    );

    @Operation(summary = "Obtener usuario por ID", description = "Recupera la información de un usuario específico mediante su identificador único"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado exitosamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping("/{user-id}")
    ResponseEntity<UserResponse> getUser(@PathVariable("user-id") Long userId);

    @Operation(summary = "Actualizar foto de perfil", description = "Actualiza la imagen de perfil de un usuario específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Foto de perfil actualizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Archivo inválido o formato no soportado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(responseCode = "409", description = "Conflicto al eliminar una imagen con respecto si la foto de perfil es propia o del sistema",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(responseCode = "413", description = "Archivo demasiado grande",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PatchMapping(value = "/{user-id}/profile-picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Void> uploadProfilePicture(
            @PathVariable("user-id") Long userId,
            @RequestPart MultipartFile file) throws IOException;

    @Operation(summary = "Actualizar la contraseña", description = "Actualizar la contraseña del usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Contraseña cambiada exitosamente",
                    content = @Content(mediaType = "application/json"))
    })
    @PatchMapping("/{user-id}/password")
    ResponseEntity<Void> updatePassword(@PathVariable("user-id") Long userId, @RequestBody @Valid UpdatePasswordRequest request);

    @Operation(summary = "Eliminar un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
                    content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("/{user-id}/delete")
    ResponseEntity<Void> deleteUser(@PathVariable("user-id") Long userId);
}
