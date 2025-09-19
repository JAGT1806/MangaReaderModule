package com.jagt.reader.auth.infrastructure.output.persistence.mapper;

import com.jagt.reader.auth.domain.model.CodeSecurity;
import com.jagt.reader.auth.infrastructure.output.persistence.entity.CodeSecurityEntity;
import com.jagt.reader.user.infrastructure.output.persistence.mapper.UserPersistenceMapper;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
        uses = UserPersistenceMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CodeSecurityPersistenceMapper {
    @Mapping(target = "id.id", source = "id")
    @Mapping(target = "audit.createdAt", source = "createdAt")
    @Mapping(target = "audit.updateAt", source = "updateAt")
    CodeSecurity toDomain(CodeSecurityEntity entity);

    @Mapping(target = "id", source = "id.id")
    @Mapping(target = "createdAt", source = "audit.createdAt")
    @Mapping(target = "updateAt", source = "audit.updateAt")
    CodeSecurityEntity toEntity(CodeSecurity domain);
}
