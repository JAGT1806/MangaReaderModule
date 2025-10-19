package com.jagt.reader.user.infrastructure.input.rest.mapper;

import com.jagt.reader.role.domain.model.Role;
import com.jagt.reader.shared.common.domain.model.Pagination;
import com.jagt.reader.user.application.command.CreateUserCommand;
import com.jagt.reader.user.application.command.UpdatePasswordCommand;
import com.jagt.reader.user.application.command.UpdateUserProfilePictureCommand;
import com.jagt.reader.user.application.query.GetUserFilterQuery;
import com.jagt.reader.user.domain.model.User;
import com.jagt.reader.user.infrastructure.input.rest.request.CreateUserRequest;
import com.jagt.reader.user.infrastructure.input.rest.request.UpdatePasswordRequest;
import com.jagt.reader.user.infrastructure.input.rest.response.UserListResponse;
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

    UserListResponse toResponse(Pagination<User> pagination);

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

    @Mapping(target = "userId", expression = "java(IDValue.builder().id(userId).build())")
    @Mapping(target = "password", source = "request.password")
    @Mapping(target = "newPassword", source = "request.newPassword")
    UpdatePasswordCommand toCommand(Long userId, UpdatePasswordRequest request);

    @Mapping(target = "username.name", source = "username")
    @Mapping(target = "email.name", source = "email")
    @Mapping(target = "role.name", source = "role")
    @Mapping(target = "pagination", expression = "java(new GetCommonQuery(offset, limit))")
    GetUserFilterQuery toQuery(String username, String email, String role, int offset, int limit, Boolean enabled);


    @Mapping(target = "username", expression = "java(NameValue.builder().name(request.username()).build())")
    @Mapping(target = "email", expression = "java(NameValue.builder().name(request.email()).build())")
    CreateUserCommand toCommand(CreateUserRequest request);
}
