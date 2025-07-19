package com.jagt.reader.role.application.usecase;

import com.jagt.reader.role.application.mapper.RoleApplicationMapper;
import com.jagt.reader.role.application.port.input.GetRoleUseCase;
import com.jagt.reader.role.application.query.GetRoleFilterQuery;
import com.jagt.reader.role.domain.exception.RoleNotFoundException;
import com.jagt.reader.role.domain.model.Role;
import com.jagt.reader.role.domain.port.output.RolePersistencePort;
import com.jagt.reader.shared.common.domain.model.Pagination;
import com.jagt.reader.shared.common.domain.model.value.IDValue;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetRoleUseCaseImpl implements GetRoleUseCase {
    private final RolePersistencePort rolePersistencePort;
    private final RoleApplicationMapper roleApplicationMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(GetRoleUseCaseImpl.class);

    @Override
    public Pagination<Role> execute(GetRoleFilterQuery query) {
        executeUseCaseLogger();

        LOGGER.info("Query: {}", query);
        List<Role> roles;
        long total;

        if (query.roleName().value() != null || query.roleName().value().trim().isEmpty()) {
            roles = rolePersistencePort.findAll(query.pagination().offset(), query.pagination().limit());
            total = rolePersistencePort.count();
        } else {
            roles = rolePersistencePort.findByFilter(query.roleName().value(), query.pagination().offset(), query.pagination().limit());
            total = rolePersistencePort.countByFilter(query.roleName().value());
        }

        finishedExecutionLogger();
        return roleApplicationMapper.toPagination(roles, query.pagination().offset(), query.pagination().limit(), total);
    }

    @Override
    public Role execute(IDValue roleId) {
        executeUseCaseLogger();
        LOGGER.info("ID: {}", roleId.value());

        finishedExecutionLogger();
        return rolePersistencePort.findById(roleId.value())
                .orElseThrow(() -> new RoleNotFoundException(String.valueOf(roleId.value())));
    }

    private void executeUseCaseLogger() {
        LOGGER.info("GetRoleUseCase execute");
    }

    private void finishedExecutionLogger() {
        LOGGER.info("End GetRoleUseCase execute");
    }
}
