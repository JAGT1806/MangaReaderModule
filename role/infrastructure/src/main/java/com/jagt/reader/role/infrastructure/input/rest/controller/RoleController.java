package com.jagt.reader.role.infrastructure.input.rest.controller;

import com.jagt.reader.role.application.port.input.CreateRoleUseCase;
import com.jagt.reader.role.application.port.input.DeleteRoleUseCase;
import com.jagt.reader.role.application.port.input.GetRoleUseCase;
import com.jagt.reader.role.application.port.input.UpdateRoleUseCase;
import com.jagt.reader.role.infrastructure.input.rest.controller.doc.RoleControllerDoc;
import com.jagt.reader.role.infrastructure.input.rest.mapper.RoleRestMapper;
import com.jagt.reader.role.infrastructure.input.rest.request.RoleRequest;
import com.jagt.reader.role.infrastructure.input.rest.response.RoleResponse;
import com.jagt.reader.shared.common.domain.model.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController implements RoleControllerDoc {
    private final GetRoleUseCase getRoleUseCase;
    private final CreateRoleUseCase createRoleUseCase;
    private final UpdateRoleUseCase updateRoleUseCase;
    private final DeleteRoleUseCase deleteRoleUseCase;
    private final RoleRestMapper mapper;

    @Override
    public ResponseEntity<Pagination<RoleResponse>> findRoles(String role, int offset, int limit) {
        return ResponseEntity.ok(mapper.toPaginationResponse(getRoleUseCase.execute(mapper.toQuery(role, offset, limit))));
    }

    @Override
    public ResponseEntity<RoleResponse> findRoleById(Long roleId) {
        return ResponseEntity.ok(mapper.toResponse(getRoleUseCase.execute(mapper.toValue(roleId))));
    }

    @Override
    public ResponseEntity<RoleResponse> createRole(RoleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toResponse(createRoleUseCase.execute(mapper.toCreateCommand(request))));
    }

    @Override
    public ResponseEntity<RoleResponse> updateRole(Long roleId, RoleRequest request) {
        return ResponseEntity.ok(mapper.toResponse(updateRoleUseCase.execute(mapper.toUpdateCommand(roleId, request))));
    }

    @Override
    public ResponseEntity<Void> deleteRole(Long roleId) {
        deleteRoleUseCase.execute(mapper.toValue(roleId));
        return ResponseEntity.noContent().build();
    }
}
