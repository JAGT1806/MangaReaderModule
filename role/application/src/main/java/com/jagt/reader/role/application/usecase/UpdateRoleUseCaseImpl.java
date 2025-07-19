package com.jagt.reader.role.application.usecase;

import com.jagt.reader.role.application.command.UpdateRoleCommand;
import com.jagt.reader.role.application.mapper.RoleApplicationMapper;
import com.jagt.reader.role.application.port.input.GetRoleUseCase;
import com.jagt.reader.role.application.port.input.UpdateRoleUseCase;
import com.jagt.reader.role.application.validation.input.Validation;
import com.jagt.reader.role.domain.exception.RoleExistException;
import com.jagt.reader.role.domain.model.Role;
import com.jagt.reader.role.domain.port.output.RolePersistencePort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateRoleUseCaseImpl implements UpdateRoleUseCase {
    private final RolePersistencePort rolePersistencePort;
    private final GetRoleUseCase getRoleUseCase;
    private final RoleApplicationMapper roleApplicationMapper;
    private final Validation validation;

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateRoleUseCaseImpl.class);

    @Override
    public Role execute(UpdateRoleCommand command) {
        LOGGER.info("UpdateRoleUseCaseImpl.execute");
        LOGGER.info("Update role: {}", command);

        validation.validateNull(command.roleId());

        Role roleToUpdate = getRoleUseCase.execute(roleApplicationMapper.toValue(command.roleId().value()));

        if (!command.roleName().value().isEmpty()) {
            Role roleWithSameName = rolePersistencePort.findByName(command.roleName().value().toUpperCase())
                    .orElse(new Role());

            if (!roleToUpdate.getId().equals(roleWithSameName.getId())) {
                throw new RoleExistException(command.roleName().value().toUpperCase());
            }

            roleToUpdate.setName(roleApplicationMapper.toValue(command.roleName().value().toUpperCase()));
        }

        roleToUpdate.getAuditTimestamps().updated();

        return rolePersistencePort.save(roleToUpdate);
    }
}
