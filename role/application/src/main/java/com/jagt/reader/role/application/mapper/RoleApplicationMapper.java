package com.jagt.reader.role.application.mapper;

import com.jagt.reader.role.application.command.CreateRoleCommand;
import com.jagt.reader.role.domain.model.Role;
import com.jagt.reader.shared.common.domain.model.Pagination;
import com.jagt.reader.shared.common.domain.model.value.IDValue;
import com.jagt.reader.shared.common.domain.model.value.NameValue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleApplicationMapper {
    @Mapping(target = "data", source = "roles")
    Pagination<Role> toPagination(List<Role> roles, int offset, int limit, long total);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", expression = "java(toValue(command.roleName().value().toUpperCase()))")
    @Mapping(target = "auditTimestamps", expression = "java(com.jagt.reader.shared.common.domain.model.value.AuditTimestampsValue.now())")
    Role toDomain(CreateRoleCommand command);

    IDValue toValue(Long id);

    NameValue toValue(String name);

}
