package com.jagt.reader.role.application.port.input;

import com.jagt.reader.role.application.command.CreateRoleCommand;
import com.jagt.reader.role.domain.model.Role;

public interface CreateRoleUseCase {
    Role execute(CreateRoleCommand command);
}
