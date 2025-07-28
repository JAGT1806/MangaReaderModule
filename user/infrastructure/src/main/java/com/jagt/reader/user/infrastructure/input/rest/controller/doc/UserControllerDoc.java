package com.jagt.reader.user.infrastructure.input.rest.controller.doc;

import com.jagt.reader.shared.common.infrastructure.input.web.response.ErrorResponse;
import com.jagt.reader.user.infrastructure.input.rest.request.CreateUserRequest;
import com.jagt.reader.user.infrastructure.input.rest.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "User Controller", description = "Operaciones relacionadas con los usuarios")
public interface UserControllerDoc {
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

    @Operation(summary = "Crear nuevo usuario", description = "Crea un nuevo usuario en el sistema con la información proporcionada"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(responseCode = "409", description = "El usuario ya existe",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping("/create")
    ResponseEntity<Void> createUser(@RequestBody @Valid CreateUserRequest request);

    @Operation(summary = "Subir foto de perfil", description = "Actualiza la imagen de perfil de un usuario específico")
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
}
