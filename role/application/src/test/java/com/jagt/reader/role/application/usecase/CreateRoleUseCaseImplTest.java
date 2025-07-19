package com.jagt.reader.role.application.usecase;

import com.jagt.reader.role.application.command.CreateRoleCommand;
import com.jagt.reader.role.application.mapper.RoleApplicationMapper;
import com.jagt.reader.role.domain.exception.RoleExistException;
import com.jagt.reader.role.domain.model.Role;
import com.jagt.reader.role.domain.port.output.RolePersistencePort;
import com.jagt.reader.shared.common.domain.model.value.NameValue;
import com.jagt.reader.shared.i18n.domain.service.MessageProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateRoleUseCaseImplTest {

    @Mock
    private RolePersistencePort rolePersistencePort;

    @Mock
    private MessageProvider messageProvider;

    @Mock
    private RoleApplicationMapper mapper;

    @InjectMocks
    private CreateRoleUseCaseImpl createRoleUseCase;

    private CreateRoleCommand command;
    private Role role;

    @BeforeEach
    void setUp() {
        command = new CreateRoleCommand(NameValue.builder().name("Admin").build());
        role = new Role();
        role.setName(NameValue.builder().name("ADMIN").build());
    }

    @Test
    void execute_WithValidCommand_ShouldCreateRole() {
        when(rolePersistencePort.existByName("ADMIN")).thenReturn(false);
        when(mapper.toDomain(command)).thenReturn(role);
        when(rolePersistencePort.save(role)).thenReturn(role);

        Role result = createRoleUseCase.execute(command);

        assertNotNull(result);
        assertEquals(role.getName(), result.getName());
        verify(rolePersistencePort).save(role);
    }

    @Test
    void execute_WithNullCommand_ShouldThrowIllegalArgumentException() {
        when(messageProvider.getMessage("role.name.error.null")).thenReturn("Nombre del rol nulo o vacío");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> createRoleUseCase.execute(null));

        assertEquals("Nombre del rol nulo o vacío", exception.getMessage());
    }

    @Test
    void execute_WithNullRoleName_ShouldThrowIllegalArgumentException() {
        CreateRoleCommand invalidCommand = new CreateRoleCommand(null);
        when(messageProvider.getMessage("role.name.error.null")).thenReturn("Nombre del rol nulo o vacío");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> createRoleUseCase.execute(invalidCommand));

        assertEquals("Nombre del rol nulo o vacío", exception.getMessage());
    }

    @Test
    void execute_WithExistingRoleName_ShouldThrowRoleExistsException() {
        when(rolePersistencePort.existByName("ADMIN")).thenReturn(true);

        RoleExistException exception = assertThrows(RoleExistException.class, () -> createRoleUseCase.execute(command));

        assertEquals("ADMIN", exception.getMessage());
    }

}