package com.jagt.reader.role.application.port.input;

import com.jagt.reader.role.application.command.UpdateRoleCommand;
import com.jagt.reader.role.domain.model.Role;

public interface UpdateRoleUseCase {
    Role execute(UpdateRoleCommand command);
}
