package com.jagt.reader.role.application.usecase;

import com.jagt.reader.role.application.mapper.RoleApplicationMapper;
import com.jagt.reader.role.application.port.input.DeleteRoleUseCase;
import com.jagt.reader.role.application.port.input.GetRoleUseCase;
import com.jagt.reader.role.application.validation.input.Validation;
// import com.jagt.reader.role.application.validation.output.RoleUsageChecker;
//import com.jagt.reader.role.domain.exception.RoleInUseException;
import com.jagt.reader.role.domain.port.output.RolePersistencePort;
import com.jagt.reader.shared.common.domain.model.value.IDValue;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteRoleUseCaseImpl implements DeleteRoleUseCase {
    private final RolePersistencePort rolePersistencePort;
    private final RoleApplicationMapper mapper;
    private final GetRoleUseCase getRoleUseCase;
//    private final RoleUsageChecker roleUsageChecker;
    private final Validation validation;

    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteRoleUseCaseImpl.class);

    @Override
    public void execute(IDValue id) {
        LOGGER.info("DeleteRoleUseCase.execute");
        LOGGER.info("IDValue: {}", id);

        validation.validateNull(id);

        //if (roleUsageChecker.isRoleInUse(mapper.toValue(id.value())))
        //    throw new RoleInUseException(getRoleUseCase.execute(id).getName().value());

        rolePersistencePort.delete(id.value());
    }
}
