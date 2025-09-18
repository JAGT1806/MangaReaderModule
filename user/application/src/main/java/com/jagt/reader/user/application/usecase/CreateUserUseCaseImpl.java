package com.jagt.reader.user.application.usecase;

import com.jagt.reader.role.application.port.input.GetRoleUseCase;
import com.jagt.reader.role.domain.model.Role;
import com.jagt.reader.shared.common.domain.model.value.NameValue;
import com.jagt.reader.shared.i18n.domain.service.MessageProvider;
import com.jagt.reader.user.application.command.CreateUserCommand;
import com.jagt.reader.user.application.mapper.UserApplicationMapper;
import com.jagt.reader.user.application.port.input.CreateUserUseCase;
import com.jagt.reader.user.domain.model.User;
import com.jagt.reader.user.domain.model.value.ProfilePicture;
import com.jagt.reader.user.domain.port.output.PasswordService;
import com.jagt.reader.user.domain.port.output.UserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CreateUserUseCaseImpl implements CreateUserUseCase {
    private final UserPersistencePort port;
    private final UserApplicationMapper mapper;
    private final MessageProvider messageProvider;
    private final PasswordService passwordService;
    private final GetRoleUseCase getRoleUseCase;

    @Override
    public User execute(CreateUserCommand command) {
        if (port.existsByEmail(command.email().value())) {
            throw new IllegalArgumentException(messageProvider.getMessage("user.email.unique"));
        }
        CreateUserCommand newCommand = new CreateUserCommand(
                command.username(), command.email(), passwordService.encode(command.password())
        );
        User user = mapper.toDomain(newCommand);
        user.setProfilePicture(ProfilePicture.defaultPicture());
        user.setEnabled(false);
        Role role = getRoleUseCase.execute(NameValue.builder().name("USER").build());
        user.setRoles(Set.of(role));

        return port.save(user);
    }
}
