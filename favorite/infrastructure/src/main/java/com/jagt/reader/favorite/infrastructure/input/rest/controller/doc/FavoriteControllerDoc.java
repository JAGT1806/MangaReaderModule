package com.jagt.reader.favorite.infrastructure.input.rest.controller.doc;

import com.jagt.reader.favorite.infrastructure.input.rest.request.AddFavoriteRequest;
import com.jagt.reader.favorite.infrastructure.input.rest.request.DeleteFavoriteRequest;
import com.jagt.reader.favorite.infrastructure.input.rest.response.FavoriteListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Favorite", description = "Operaciones relacionadas con los mangas favoritos del usuario")
@SecurityRequirement(name = "bearerAuth")
public interface FavoriteControllerDoc {
    @Operation(summary = "Obtener todos los mangas favoritos", description = "Recupera la información de los mangas favoritos de todos los usuarios de forma paginada")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Página de favoritos encontrado exitosamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = FavoriteListResponse.class))
            )
    })
    @GetMapping
    ResponseEntity<FavoriteListResponse> getAllFavorites(
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "12") int limit
    );

    @Operation(summary = "Obtener todos los mangas favoritos de un usuario", description = "Recupera la información de los mangas favoritos de un usuario por ID de forma paginada")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Página de favoritos encontrado exitosamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = FavoriteListResponse.class))
            )
    })
    @GetMapping("/user/{user-id}")
    ResponseEntity<FavoriteListResponse> getFavoritesByUserId(
            @PathVariable("user-id") Long userId,
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "12") int limit
    );

    @Operation(summary = "Añadir un manga a favoríto a un usuario.", description = "Adiciona un manga a la lista de favoritos del usuario.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Manga añadido exitosamente a la lista de favoritos de usuario")
    })
    @PostMapping("/user/{user-id}")
    ResponseEntity<Void> addFavorite(@PathVariable("user-id") Long userId, @RequestBody AddFavoriteRequest request);

    @Operation(summary = "Elimina un manga de favoríto de un usuario.", description = "Elimina un manga de la lista de favoritos del usuario.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Manga eliminado exitosamente de la lista de favoritos de usuario")
    })
    @DeleteMapping("/user/{user-id}")
    ResponseEntity<Void> deleteFavorite(@PathVariable("user-id") Long userId, @RequestBody DeleteFavoriteRequest request);
}
