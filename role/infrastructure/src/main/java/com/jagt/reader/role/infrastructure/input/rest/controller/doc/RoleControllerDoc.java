package com.jagt.reader.role.infrastructure.input.rest.controller.doc;

import com.jagt.reader.role.infrastructure.input.rest.request.RoleRequest;
import com.jagt.reader.role.infrastructure.input.rest.response.RoleResponse;
import com.jagt.reader.shared.common.domain.model.Pagination;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Role Controller", description = "Operaciones relacionadas con los clientes")
public interface RoleControllerDoc {
    @Operation(summary = "Obtener los roles paginados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Roles encontrados exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pagination.class)))
    })
    @GetMapping
    ResponseEntity<Pagination<RoleResponse>> findRoles(
            @RequestParam(required = false, defaultValue = "") String role,
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "12") int limit
    );

    @Operation(summary = "Obtener el rol por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol encontrados exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RoleResponse.class)))
    })
    @GetMapping("/{role-id}")
    ResponseEntity<RoleResponse> findRoleById(@PathVariable("role-id") Long roleId);

    @Operation(summary = "Añadir un rol al sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rol creado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RoleRequest.class)))
    })
    @PostMapping("/create")
    ResponseEntity<RoleResponse> createRole(@RequestBody RoleRequest request);

    @Operation(summary = "Actualizar un rol al sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rol actualizado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RoleRequest.class)))
    })
    @PutMapping("/{role-id}/update")
    ResponseEntity<RoleResponse> updateRole(@PathVariable("role-id") Long roleId, @RequestBody RoleRequest request);

    @Operation(summary = "Eliminar un rol del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Rol eliminado exitosamente",
                    content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("/{role-id}/delete")
    ResponseEntity<Void> deleteRole(@PathVariable("role-id") Long roleId);
}
