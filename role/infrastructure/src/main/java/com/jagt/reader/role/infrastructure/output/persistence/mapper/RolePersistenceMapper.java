package com.jagt.reader.role.infrastructure.output.persistence.mapper;

import com.jagt.reader.role.domain.model.Role;
import com.jagt.reader.role.infrastructure.output.persistence.entity.RoleEntity;
import com.jagt.reader.shared.common.domain.model.value.AuditTimestampsValue;
import com.jagt.reader.shared.common.domain.model.value.IDValue;
import com.jagt.reader.shared.common.domain.model.value.NameValue;
import org.mapstruct.*;

@Mapper(componentModel = "spring", imports = {AuditTimestampsValue.class})
public interface RolePersistenceMapper {

    @Mapping(source = "id", target = "id", qualifiedByName = "mapToIDValue")
    @Mapping(source = "name", target = "name", qualifiedByName = "mapToNameValue")
    @Mapping(target = "auditTimestamps", expression = "java(new AuditTimestampsValue(entity.getCreatedAt(), entity.getUpdatedAt()))")
    Role toDomain(RoleEntity entity);

    @Mapping(source = "id", target = "id", qualifiedByName = "mapFromIDValue")
    @Mapping(source = "name", target = "name", qualifiedByName = "mapFromNameValue")
    @Mapping(target = "createdAt", expression = "java(role.getAuditTimestamps().createdAt())")
    @Mapping(target = "updatedAt", expression = "java(role.getAuditTimestamps().updatedAt())")
    RoleEntity toEntity(Role role);

    @Named("mapToIDValue")
    static IDValue mapToIDValue(Long id) {
        return IDValue.builder().id(id).build();
    }

    @Named("mapToNameValue")
    static NameValue mapToNameValue(String name) {
        return NameValue.builder().name(name).build();
    }

    @Named("mapFromIDValue")
    static Long mapFromIDValue(IDValue idValue) {
        return idValue != null ? idValue.value() : null;
    }

    @Named("mapFromNameValue")
    static String mapFromNameValue(NameValue nameValue) {
        return nameValue != null ? nameValue.value() : null;
    }
}