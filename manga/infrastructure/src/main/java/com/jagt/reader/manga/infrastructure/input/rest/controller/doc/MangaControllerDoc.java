package com.jagt.reader.manga.infrastructure.input.rest.controller.doc;

import com.jagt.reader.manga.infrastructure.input.rest.response.ChapterResponse;
import com.jagt.reader.manga.infrastructure.input.rest.response.FeedResponse;
import com.jagt.reader.manga.infrastructure.input.rest.response.MangaResponse;
import com.jagt.reader.shared.common.domain.model.Pagination;
import com.jagt.reader.shared.common.infrastructure.input.web.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Manga", description = "Operaciones relacionadas con la consulta de mangas a la API de MangaDex")
public interface MangaControllerDoc {

    @Operation(summary = "Obtener mangas", description = "Obtiene la información de los mangas de la API de MangaDex")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mangas encontrados exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pagination.class))),
            @ApiResponse(responseCode = "502", description = "Error con la conexión de la API", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    ResponseEntity<Pagination<MangaResponse>> getMangas(
            @RequestParam(required = false, defaultValue = "") String title,
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "12") int limit,
            @RequestParam(required = false, defaultValue = "false") boolean nsfw
    );


    @Operation(summary = "Obtener manga por id", description = "Obtiene la información del manga por id de la API de MangaDex")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Manga encontrado exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MangaResponse.class))),
            @ApiResponse(responseCode = "502", description = "Error con la conexión de la API", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    ResponseEntity<MangaResponse> getMangaById(@PathVariable String id);

    @Operation(summary = "Obtener el contenido de un manga", description = "Obtiene los capítulos y volúmenes de los mangas de la API de MangaDex")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contenido traído exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FeedResponse.class))),
            @ApiResponse(responseCode = "502", description = "Error con la conexión de la API", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}/feed")
    ResponseEntity<FeedResponse> getMangaFeeds(
            @PathVariable String id,
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "100") int limit,
            @RequestParam(required = false, defaultValue = "false") boolean nsfw
    );


    @Operation(summary = "Obtener contenido de un capítulo", description = "Obtiene las imágenes de un capítulo de un manga de la API de MangaDex")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Datos encontrados", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ChapterResponse.class))),
            @ApiResponse(responseCode = "502", description = "Error con la conexión de la API", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/chapter/{id}")
    ResponseEntity<ChapterResponse> getChapter(@PathVariable String id);
}
