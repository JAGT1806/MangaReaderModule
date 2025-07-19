package com.jagt.reader.role.application.usecase;

import com.jagt.reader.role.application.mapper.RoleApplicationMapper;
import com.jagt.reader.role.application.port.input.GetRoleUseCase;
import com.jagt.reader.role.application.validation.input.Validation;
import com.jagt.reader.role.domain.model.Role;
import com.jagt.reader.role.domain.port.output.RolePersistencePort;
import com.jagt.reader.shared.common.domain.model.value.IDValue;
import com.jagt.reader.shared.common.domain.model.value.NameValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteRoleUseCaseImplTest {

    @Mock
    private RolePersistencePort rolePersistencePort;

    @Mock
    private RoleApplicationMapper mapper;

    @Mock
    private GetRoleUseCase getRoleUseCase;

    @Mock
    private Validation validation;

    @InjectMocks
    private DeleteRoleUseCaseImpl deleteRoleUseCase;

    private IDValue validId;
    private Role role;

    @BeforeEach
    void setUp() {
        validId = IDValue.builder().id(1L).build();
        role = new Role();
        role.setId(validId);
        role.setName(NameValue.builder().name("ADMIN").build());
    }

    @Test
    void execute_WithValidId_ShouldDeleteRole() {
        doNothing().when(validation).validateNull(validId);
        doNothing().when(rolePersistencePort).delete(validId.value());

        assertDoesNotThrow(() -> deleteRoleUseCase.execute(validId));

        verify(rolePersistencePort).delete(validId.value());
    }

    @Test
    void execute_WithNullId_ShouldThrowValidationException() {
        doThrow(new IllegalArgumentException("ID cannot be null")).when(validation).validateNull(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> deleteRoleUseCase.execute(null));

        assertEquals("ID cannot be null", exception.getMessage());
    }

    // Test para cuando se descomente el código de RoleInUseException
    /*
    @Test
    void execute_WhenRoleIsInUse_ShouldThrowRoleInUseException() {
        when(roleUsageChecker.isRoleInUse(any())).thenReturn(true);
        when(getRoleUseCase.execute(validId)).thenReturn(existingRole);

        RoleInUseException exception = assertThrows(RoleInUseException.class,
                () -> deleteRoleUseCase.execute(validId));

        assertEquals("ADMIN", exception.getRoleName());
    }
    */

}