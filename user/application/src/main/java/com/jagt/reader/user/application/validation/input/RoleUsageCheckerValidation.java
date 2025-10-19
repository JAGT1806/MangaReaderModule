package com.jagt.reader.user.application.validation.input;

import com.jagt.reader.role.application.validation.input.Validation;
import com.jagt.reader.role.application.validation.output.RoleUsageChecker;
import com.jagt.reader.shared.common.domain.model.value.IDValue;
import com.jagt.reader.user.domain.model.User;
import com.jagt.reader.user.domain.port.output.UserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RoleUsageCheckerValidation implements RoleUsageChecker {
    private final UserPersistencePort userPersistencePort;
    private final Validation validation;

    @Override
    public boolean isRoleInUse(IDValue roleId) {
        validation.validateNull(roleId);

        List<User> userList = userPersistencePort.findAllByRoleId(roleId);

        return !userList.isEmpty();
    }
}
