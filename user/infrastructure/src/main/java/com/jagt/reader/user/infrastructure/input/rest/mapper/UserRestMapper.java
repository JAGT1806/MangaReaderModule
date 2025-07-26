package com.jagt.reader.user.infrastructure.input.rest.mapper;

import com.jagt.reader.role.domain.model.Role;
import com.jagt.reader.user.application.command.CreateUserCommand;
import com.jagt.reader.user.application.command.UpdateUserProfilePictureCommand;
import com.jagt.reader.user.domain.model.User;
import com.jagt.reader.user.infrastructure.input.rest.request.CreateUserRequest;
import com.jagt.reader.user.infrastructure.input.rest.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserRestMapper {
    @Mapping(target = "id", source = "id.id")
    @Mapping(target = "username", source = "userValue.username")
    @Mapping(target = "email", source = "userValue.email")
    @Mapping(target = "profilePictureUrl", source = "profilePicture.url")
    @Mapping(target = "createdAt", source = "auditTimestamps.createdAt")
    @Mapping(target = "updatedAt", source = "auditTimestamps.updatedAt")
    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapRolesToStrings")
    UserResponse toResponse(User user);

    @Named("mapRolesToStrings")
    default Set<String> mapRolesToStrings(Set<Role> roles) {
        if (roles == null) {
            return new HashSet<>();
        }
        return roles.stream()
                .map(role -> role.getName().value())
                .collect(Collectors.toSet());
    }

    @Mapping(target = "userId", expression = "java(IDValue.builder().id(userId).build())")
    UpdateUserProfilePictureCommand toCommand(Long userId, MultipartFile file);


    @Mapping(target = "username", expression = "java(NameValue.builder().name(request.username()).build())")
    @Mapping(target = "email", expression = "java(NameValue.builder().name(request.email()).build())")
    CreateUserCommand toCommand(CreateUserRequest request);
}
