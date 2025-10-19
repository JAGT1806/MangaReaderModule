package com.jagt.reader.user.infrastructure.input.rest.controller;

import com.jagt.reader.shared.common.domain.model.value.IDValue;
import com.jagt.reader.user.application.port.input.DeleteUserUseCase;
import com.jagt.reader.user.application.port.input.GetUserUseCase;
import com.jagt.reader.user.application.port.input.UpdatePasswordUseCase;
import com.jagt.reader.user.application.port.input.UpdateUserUseCase;
import com.jagt.reader.user.infrastructure.input.rest.controller.doc.UserControllerDoc;
import com.jagt.reader.user.infrastructure.input.rest.mapper.UserRestMapper;
import com.jagt.reader.user.infrastructure.input.rest.request.UpdatePasswordRequest;
import com.jagt.reader.user.infrastructure.input.rest.response.UserListResponse;
import com.jagt.reader.user.infrastructure.input.rest.response.UserResponse;
import com.jagt.reader.user.infrastructure.input.rest.validation.ProfilePictureValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController implements UserControllerDoc {
    private final GetUserUseCase getUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final UpdatePasswordUseCase updatePasswordUseCase;
    private final UserRestMapper mapper;
    private final ProfilePictureValidation profilePictureValidation;
    private final DeleteUserUseCase deleteUserUseCase;

    @Override
    public UserListResponse getUsers(String username, String email, String role, int offset, int limit, Boolean enabled) {
        return mapper.toResponse(getUserUseCase.execute(mapper.toQuery(username, email, role, offset, limit, enabled)));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or @userSecurityExpression.canAccessUser(#userId)")
    public ResponseEntity<UserResponse> getUser(Long userId) {
        return ResponseEntity.ok(mapper.toResponse(getUserUseCase.execute(userId)));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or @userSecurityExpression.canAccessUser(#userId)")
    public ResponseEntity<Void> uploadProfilePicture(
            @PathVariable("user-id") Long userId,
            @RequestPart MultipartFile file) throws IOException {

        profilePictureValidation.validate(file);

        updateUserUseCase.execute(mapper.toCommand(userId, file));

        return ResponseEntity.ok().build();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or @userSecurityExpression.canAccessUser(#userId)")
    public ResponseEntity<Void> updatePassword(Long userId, UpdatePasswordRequest request) {
        updatePasswordUseCase.execute(
                mapper.toCommand(userId, request)
        );
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteUser(Long userId) {
        deleteUserUseCase.execute(IDValue.builder().id(userId).build());
        return ResponseEntity.noContent().build();
    }
}
