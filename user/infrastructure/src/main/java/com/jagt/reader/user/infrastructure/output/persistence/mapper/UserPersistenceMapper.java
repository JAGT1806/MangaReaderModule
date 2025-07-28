package com.jagt.reader.user.infrastructure.output.persistence.mapper;

import com.jagt.reader.role.infrastructure.output.persistence.mapper.RolePersistenceMapper;
import com.jagt.reader.shared.common.domain.model.value.IDValue;
import com.jagt.reader.user.domain.model.User;
import com.jagt.reader.user.domain.model.value.ProfilePicture;
import com.jagt.reader.user.domain.model.value.UserValue;
import com.jagt.reader.user.infrastructure.output.persistence.entity.UserEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(
        componentModel = "spring",
        uses = RolePersistenceMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface UserPersistenceMapper {
    @Mapping(target = "id", source = "id", qualifiedByName = "longToIDValue")
    @Mapping(target = "userValue", source = ".")
    @Mapping(target = "auditTimestamps", expression = "java(new AuditTimestampsValue(entity.getDateCreate(), entity.getDateUpdate()))")
    @Mapping(target = "profilePicture", source = ".")
    @Mapping(target = "roles", source = "roles")
    User toDomain(UserEntity entity);

    @Mapping(target = "id", source = "id.id")
    @Mapping(target = "username", source = "userValue.username")
    @Mapping(target = "email", source = "userValue.email")
    @Mapping(target = "password", source = "userValue.password")
    @Mapping(target = "enabled", source = "enabled")
    @Mapping(target = "default", source = "profilePicture.default")
    @Mapping(target = "fileName", source = "profilePicture.fileName")
    @Mapping(target = "dateCreate", source = "auditTimestamps.createdAt")
    @Mapping(target = "dateUpdate", source = "auditTimestamps.updatedAt")
    @Mapping(target = "roles", source = "roles")
    UserEntity toEntity(User user);

    @Named("longToIDValue")
    default IDValue longToIDValue(Long id) {
        return id != null ? IDValue.builder().id(id).build() : null;
    }

    default UserValue mapUserValue(UserEntity entity) {
        if (entity == null) return null;
        return UserValue.builder()
                .username(entity.getUsername())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .build();
    }

    default ProfilePicture mapProfilePicture(UserEntity entity) {
        if (entity == null) return null;
        return ProfilePicture.builder()
                .fileName(entity.getFileName())
                .isDefault(entity.isDefault())
                .build();
    }
}
