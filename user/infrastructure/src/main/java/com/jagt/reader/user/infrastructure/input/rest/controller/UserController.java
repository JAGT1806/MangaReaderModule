package com.jagt.reader.user.infrastructure.input.rest.controller;

import com.jagt.reader.user.application.port.input.CreateUserUseCase;
import com.jagt.reader.user.application.port.input.GetUserUseCase;
import com.jagt.reader.user.application.port.input.UpdateUserUseCase;
import com.jagt.reader.user.infrastructure.input.rest.controller.doc.UserControllerDoc;
import com.jagt.reader.user.infrastructure.input.rest.mapper.UserRestMapper;
import com.jagt.reader.user.infrastructure.input.rest.request.CreateUserRequest;
import com.jagt.reader.user.infrastructure.input.rest.response.UserResponse;
import com.jagt.reader.user.infrastructure.input.rest.validation.ProfilePictureValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController implements UserControllerDoc {
    private final GetUserUseCase getUserUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final UserRestMapper mapper;
    private final ProfilePictureValidation profilePictureValidation;

    @Override
    public ResponseEntity<UserResponse> getUser(Long userId) {
        return ResponseEntity.ok(mapper.toResponse(getUserUseCase.execute(userId)));
    }

    @Override
    public ResponseEntity<Void> createUser(@RequestBody CreateUserRequest request) {
        createUserUseCase.execute(mapper.toCommand(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> uploadProfilePicture(
            @PathVariable("user-id") Long userId,
            @RequestPart MultipartFile file) throws IOException {

        profilePictureValidation.validate(file);

        updateUserUseCase.execute(mapper.toCommand(userId, file));

        return ResponseEntity.ok().build();
    }
}
