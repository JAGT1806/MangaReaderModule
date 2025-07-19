package com.jagt.reader.role.infrastructure.input.rest.mapper;

import com.jagt.reader.role.application.command.CreateRoleCommand;
import com.jagt.reader.role.application.command.UpdateRoleCommand;
import com.jagt.reader.role.application.query.GetRoleFilterQuery;
import com.jagt.reader.role.domain.model.Role;
import com.jagt.reader.role.infrastructure.input.rest.request.RoleRequest;
import com.jagt.reader.role.infrastructure.input.rest.response.RoleResponse;
import com.jagt.reader.shared.common.application.query.GetCommonQuery;
import com.jagt.reader.shared.common.domain.model.Pagination;
import com.jagt.reader.shared.common.domain.model.value.IDValue;
import com.jagt.reader.shared.common.domain.model.value.NameValue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleRestMapper {
    @Mapping(target = "roleName", source = "roleName", qualifiedByName = "toNameValue")
    CreateRoleCommand toCreateCommand(RoleRequest request);

    @Mapping(target = "roleId", source = "id", qualifiedByName = "toIDValue")
    @Mapping(target = "roleName", source = "request.roleName", qualifiedByName = "toNameValue")
    UpdateRoleCommand toUpdateCommand(Long id, RoleRequest request);

    @Mapping(target = "id", source = "id.id")
    @Mapping(target = "roleName", source = "name.name")
    @Mapping(target = "createdAt", source = "auditTimestamps.createdAt")
    @Mapping(target = "updatedAt", source = "auditTimestamps.updatedAt")
    RoleResponse toResponse(Role role);


    default GetRoleFilterQuery toQuery(String roleName, int offset, int limit) {
        return new GetRoleFilterQuery(
                toNameValue(roleName),
                new GetCommonQuery(offset, limit)
        );
    }


    IDValue toValue(Long id);

    default Pagination<RoleResponse> toPaginationResponse(Pagination<Role> page) {
        return Pagination.<RoleResponse>builder()
                .data(toResponseList(page.getData()))
                .offset(page.getOffset())
                .limit(page.getLimit())
                .total(page.getTotal())
                .build();
    }

    List<RoleResponse> toResponseList(List<Role> roles);

    @Named("toNameValue")
    static NameValue toNameValue(String name) {
        return NameValue.builder().name(name).build();
    }

    @Named("toIDValue")
    static IDValue toIDValue(Long id) {
        return IDValue.builder().id(id).build();
    }
}
