package com.jagt.reader.role.infrastructure.input.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jagt.reader.role.application.command.CreateRoleCommand;
import com.jagt.reader.role.application.command.UpdateRoleCommand;
import com.jagt.reader.role.application.port.input.CreateRoleUseCase;
import com.jagt.reader.role.application.port.input.DeleteRoleUseCase;
import com.jagt.reader.role.application.port.input.GetRoleUseCase;
import com.jagt.reader.role.application.port.input.UpdateRoleUseCase;
import com.jagt.reader.role.application.query.GetRoleFilterQuery;
import com.jagt.reader.role.domain.model.Role;
import com.jagt.reader.role.infrastructure.input.rest.mapper.RoleRestMapper;
import com.jagt.reader.role.infrastructure.input.rest.request.CreateRoleRequest;
import com.jagt.reader.role.infrastructure.input.rest.response.RoleResponse;
import com.jagt.reader.shared.common.domain.model.Pagination;
import com.jagt.reader.shared.common.domain.model.value.AuditTimestampsValue;
import com.jagt.reader.shared.common.domain.model.value.IDValue;
import com.jagt.reader.shared.common.domain.model.value.NameValue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoleController.class)
@ContextConfiguration(classes = {RoleController.class})
class RoleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GetRoleUseCase getRoleUseCase;

    @MockitoBean
    private CreateRoleUseCase createRoleUseCase;

    @MockitoBean
    private UpdateRoleUseCase updateRoleUseCase;

    @MockitoBean
    private DeleteRoleUseCase deleteRoleUseCase;

    @MockitoBean
    private RoleRestMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;

    private final LocalDateTime now = LocalDateTime.now();

    @Test
    void shouldReturnRoleById() throws Exception {
        Long roleId = 1L;
        Role role = Role.builder()
                .id(IDValue.builder().id(roleId).build())
                .name(NameValue.builder().name("ADMIN").build())
                .auditTimestamps(new AuditTimestampsValue(now, now))
                .build();

        RoleResponse response = new RoleResponse(roleId, "ADMIN", now, now);

        when(mapper.toValue(roleId)).thenReturn(role.getId());
        when(getRoleUseCase.execute(role.getId())).thenReturn(role);
        when(mapper.toResponse(role)).thenReturn(response);

        mockMvc.perform(get("/api/roles/{role-id}", roleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(roleId))
                .andExpect(jsonPath("$.roleName").value("ADMIN"));
    }

    @Test
    void shouldReturnPaginatedRoles() throws Exception {
        Role role = Role.builder()
                .id(IDValue.builder().id(1L).build())
                .name(NameValue.builder().name("USER").build())
                .auditTimestamps(AuditTimestampsValue.now())
                .build();

        RoleResponse response = new RoleResponse(1L, "USER", now, now);
        Pagination<Role> pagination = Pagination.<Role>builder()
                .data(List.of(role))
                .offset(0)
                .limit(10)
                .total(1)
                .build();

        Pagination<RoleResponse> responsePage = Pagination.<RoleResponse>builder()
                .data(List.of(response))
                .offset(0)
                .limit(10)
                .total(1)
                .build();

        when(mapper.toQuery("", 0, 10)).thenCallRealMethod();
        when(getRoleUseCase.execute(any(GetRoleFilterQuery.class))).thenReturn(pagination);
        when(mapper.toPaginationResponse(pagination)).thenReturn(responsePage);

        mockMvc.perform(get("/api/roles")
                        .param("role", "")
                        .param("offset", "0")
                        .param("limit", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].roleName").value("USER"));
    }

    @Test
    void shouldCreateRoleSuccessfully() throws Exception {
        CreateRoleRequest request = new CreateRoleRequest("ADMIN");
        Role role = Role.builder()
                .id(IDValue.builder().id(1L).build())
                .name(NameValue.builder().name("ADMIN").build())
                .auditTimestamps(AuditTimestampsValue.now())
                .build();

        RoleResponse response = new RoleResponse(1L, "ADMIN", now, now);

        when(mapper.toCreateCommand(request)).thenReturn(mock(CreateRoleCommand.class));
        when(createRoleUseCase.execute(any(CreateRoleCommand.class))).thenReturn(role);
        when(mapper.toResponse(role)).thenReturn(response);

        mockMvc.perform(post("/api/roles/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.roleName").value("ADMIN"));
    }

    @Test
    void shouldUpdateRoleSuccessfully() throws Exception {
        Long roleId = 1L;
        CreateRoleRequest request = new CreateRoleRequest("ADMIN_UPDATED");
        Role updatedRole = Role.builder()
                .id(IDValue.builder().id(roleId).build())
                .name(NameValue.builder().name("ADMIN_UPDATED").build())
                .auditTimestamps(AuditTimestampsValue.now())
                .build();

        RoleResponse response = new RoleResponse(roleId, "ADMIN_UPDATED", now, now);

        UpdateRoleCommand updateRoleCommand = mock(UpdateRoleCommand.class);

        when(mapper.toUpdateCommand(roleId, request)).thenReturn(updateRoleCommand);
        when(updateRoleUseCase.execute(any(UpdateRoleCommand.class))).thenReturn(updatedRole);
        when(mapper.toResponse(updatedRole)).thenReturn(response);

        mockMvc.perform(put("/api/roles/{role-id}/update", roleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roleName").value("ADMIN_UPDATED"));
    }

    @Test
    void shouldDeleteRoleSuccessfully() throws Exception {
        Long roleId = 1L;

        when(mapper.toValue(roleId)).thenReturn(IDValue.builder().id(roleId).build());

        mockMvc.perform(delete("/api/roles/{role-id}/delete", roleId))
                .andExpect(status().isNoContent());

        verify(deleteRoleUseCase).execute(any(IDValue.class));
    }

}