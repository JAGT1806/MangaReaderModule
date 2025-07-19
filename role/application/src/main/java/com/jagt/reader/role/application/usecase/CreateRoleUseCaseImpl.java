package com.jagt.reader.role.application.usecase;

import com.jagt.reader.role.application.command.CreateRoleCommand;
import com.jagt.reader.role.application.mapper.RoleApplicationMapper;
import com.jagt.reader.role.application.port.input.CreateRoleUseCase;
import com.jagt.reader.role.domain.exception.RoleExistException;
import com.jagt.reader.role.domain.model.Role;
import com.jagt.reader.role.domain.port.output.RolePersistencePort;
import com.jagt.reader.shared.i18n.domain.service.MessageProvider;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateRoleUseCaseImpl implements CreateRoleUseCase {
    private final RolePersistencePort rolePersistencePort;
    private final MessageProvider messageProvider;
    private final RoleApplicationMapper roleApplicationMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateRoleUseCaseImpl.class);

    @Override
    public Role execute(CreateRoleCommand command) {
        LOGGER.info("CreateRoleUseCaseImpl.execute");
        LOGGER.info("Command: {}", command);

        if (command == null || command.roleName() == null || command.roleName().value() == null || command.roleName().value().isEmpty()) {
            throw new IllegalArgumentException(messageProvider.getMessage("role.name.error.null"));
        }

        if (rolePersistencePort.existByName(command.roleName().value().toUpperCase())) {
            throw new RoleExistException(command.roleName().value().toUpperCase());
        }

        Role role = roleApplicationMapper.toDomain(command);

        LOGGER.info("Role: {}", role);
        return rolePersistencePort.save(role);
    }
}
