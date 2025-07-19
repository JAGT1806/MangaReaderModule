package com.jagt.reader.role.application.usecase;

import com.jagt.reader.role.application.command.UpdateRoleCommand;
import com.jagt.reader.role.application.mapper.RoleApplicationMapper;
import com.jagt.reader.role.application.port.input.GetRoleUseCase;
import com.jagt.reader.role.application.validation.input.Validation;
import com.jagt.reader.role.domain.exception.RoleExistException;
import com.jagt.reader.role.domain.model.Role;
import com.jagt.reader.role.domain.port.output.RolePersistencePort;
import com.jagt.reader.shared.common.domain.model.value.AuditTimestampsValue;
import com.jagt.reader.shared.common.domain.model.value.IDValue;
import com.jagt.reader.shared.common.domain.model.value.NameValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateRoleUseCaseImplTest {

    @Mock
    private RolePersistencePort rolePersistencePort;
    @Mock
    private GetRoleUseCase getRoleUseCase;
    @Mock
    private RoleApplicationMapper roleApplicationMapper;
    @Mock
    private Validation validation;

    @InjectMocks
    private UpdateRoleUseCaseImpl updateRoleUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldUpdateRoleSuccessfully() {
        // Arrange
        IDValue roleId = IDValue.builder().id(1L).build();
        NameValue newName = NameValue.builder().name("ADMIN").build();
        UpdateRoleCommand command = new UpdateRoleCommand(roleId, newName);

        Role existingRole = Role.builder()
                .id(roleId)
                .name(NameValue.builder().name("USER").build())
                .auditTimestamps(AuditTimestampsValue.now())
                .build();

        Role mappedRole = Role.builder()
                .id(roleId)
                .name(NameValue.builder().name("ADMIN").build())
                .auditTimestamps(existingRole.getAuditTimestamps())
                .build();

        when(roleApplicationMapper.toValue(roleId.value())).thenReturn(roleId);
        when(getRoleUseCase.execute(roleId)).thenReturn(existingRole);
        when(rolePersistencePort.findByName("ADMIN")).thenReturn(Optional.of(
                Role.builder().id(roleId).build()
        ));
        when(roleApplicationMapper.toValue("ADMIN")).thenReturn(newName);
        when(rolePersistencePort.save(any())).thenReturn(mappedRole);

        // Act
        Role result = updateRoleUseCase.execute(command);

        // Assert
        assertEquals("ADMIN", result.getName().value());
        verify(rolePersistencePort).save(any(Role.class));
    }

    @Test
    void shouldThrowExceptionWhenRoleIdIsNull() {
        // Arrange
        UpdateRoleCommand command = new UpdateRoleCommand(null, NameValue.builder().name("ADMIN").build());

        doThrow(new IllegalArgumentException("id.null")).when(validation).validateNull(null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            updateRoleUseCase.execute(command);
        });

        assertEquals("id.null", exception.getMessage());
    }

    @Test
    void shouldThrowRoleExistExceptionWhenNameAlreadyExists() {
        // Arrange
        IDValue roleId = IDValue.builder().id(1L).build();
        NameValue newName = NameValue.builder().name("admin").build();
        UpdateRoleCommand command = new UpdateRoleCommand(roleId, newName);

        Role roleToUpdate = Role.builder()
                .id(roleId)
                .name(NameValue.builder().name("USER").build())
                .auditTimestamps(AuditTimestampsValue.now())
                .build();

        Role anotherRoleWithSameName = Role.builder()
                .id(IDValue.builder().id(2L).build())
                .name(NameValue.builder().name("ADMIN").build())
                .build();

        when(roleApplicationMapper.toValue(roleId.value())).thenReturn(roleId);
        when(getRoleUseCase.execute(roleId)).thenReturn(roleToUpdate);
        when(rolePersistencePort.findByName("ADMIN")).thenReturn(Optional.of(anotherRoleWithSameName));

        // Act & Assert
        assertThrows(RoleExistException.class, () -> {
            updateRoleUseCase.execute(command);
        });
    }
}
