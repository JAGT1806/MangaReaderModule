package com.jagt.reader.role.application.usecase;

import com.jagt.reader.role.application.mapper.RoleApplicationMapper;
import com.jagt.reader.role.application.query.GetRoleFilterQuery;
import com.jagt.reader.role.domain.exception.RoleNotFoundException;
import com.jagt.reader.role.domain.model.Role;
import com.jagt.reader.role.domain.port.output.RolePersistencePort;
import com.jagt.reader.shared.common.application.query.GetCommonQuery;
import com.jagt.reader.shared.common.domain.model.Pagination;
import com.jagt.reader.shared.common.domain.model.value.IDValue;
import com.jagt.reader.shared.common.domain.model.value.NameValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetRoleUseCaseImplTest {
    @Mock
    private RolePersistencePort rolePersistencePort;

    @Mock
    private RoleApplicationMapper roleApplicationMapper;

    @InjectMocks
    private GetRoleUseCaseImpl getRoleUseCase;

    private GetRoleFilterQuery queryWithName;
    private GetRoleFilterQuery queryWithoutName;
    private Role sampleRole;
    private Pagination<Role> expectedPagination;

    @BeforeEach
    void setUp() {
        queryWithName = new GetRoleFilterQuery(NameValue.builder().name("Admin").build(), new GetCommonQuery(0, 10));
        queryWithoutName = new GetRoleFilterQuery(NameValue.builder().name("").build(), new GetCommonQuery(0, 10));
        sampleRole = new Role();
        sampleRole.setId(IDValue.builder().id(1L).build());
        sampleRole.setName(NameValue.builder().name("ADMIN").build());

        List<Role> roles = Collections.singletonList(sampleRole);
        expectedPagination = new Pagination<>(roles, 0, 10, 1);
    }

    @Test
    void execute_WithFilterQueryAndName_ShouldReturnFilteredRoles() {
        when(rolePersistencePort.findByFilter("Admin", 0, 10)).thenReturn(Collections.singletonList(sampleRole));
        when(rolePersistencePort.countByFilter("Admin")).thenReturn(1L);
        when(roleApplicationMapper.toPagination(any(), anyInt(), anyInt(), anyLong()))
                .thenReturn(expectedPagination);

        Pagination<Role> result = getRoleUseCase.execute(queryWithName);

        assertNotNull(result);
        assertEquals(1, result.getTotal());
        verify(rolePersistencePort).findByFilter("Admin", 0, 10);
    }

    @Test
    void execute_WithFilterQueryWithoutName_ShouldReturnAllRoles() {
        when(rolePersistencePort.findAll(0, 10)).thenReturn(Collections.singletonList(sampleRole));
        when(rolePersistencePort.count()).thenReturn(1L);
        when(roleApplicationMapper.toPagination(any(), anyInt(), anyInt(), anyLong()))
                .thenReturn(expectedPagination);

        Pagination<Role> result = getRoleUseCase.execute(queryWithoutName);

        assertNotNull(result);
        assertEquals(1, result.getTotal());
        verify(rolePersistencePort).findAll(0, 10);
    }

    @Test
    void execute_WithEmptyName_ShouldReturnAllRoles() {
        GetRoleFilterQuery queryWithEmptyName = new GetRoleFilterQuery(NameValue.builder().name("").build(), new GetCommonQuery(0, 10));
        when(rolePersistencePort.findAll(0, 10)).thenReturn(Collections.singletonList(sampleRole));
        when(rolePersistencePort.count()).thenReturn(1L);
        when(roleApplicationMapper.toPagination(any(), anyInt(), anyInt(), anyLong()))
                .thenReturn(expectedPagination);

        Pagination<Role> result = getRoleUseCase.execute(queryWithEmptyName);

        assertNotNull(result);
        assertEquals(1, result.getTotal());
        verify(rolePersistencePort).findAll(0, 10);
    }

    @Test
    void execute_WithValidId_ShouldReturnRole() {
        IDValue roleId = IDValue.builder().id(1L).build();
        when(rolePersistencePort.findById(1L)).thenReturn(Optional.of(sampleRole));

        Role result = getRoleUseCase.execute(roleId);

        assertNotNull(result);
        assertEquals(1L, result.getId().value());
        assertEquals("ADMIN", result.getName().value());
    }

    @Test
    void execute_WithInvalidId_ShouldThrowRoleNotFoundException() {
        IDValue invalidId = IDValue.builder().id(999L).build();
        when(rolePersistencePort.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RoleNotFoundException.class, () -> getRoleUseCase.execute(invalidId));
    }
}