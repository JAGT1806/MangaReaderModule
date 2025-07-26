package com.jagt.reader.user.application.usecase;

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

@Service
@RequiredArgsConstructor
public class CreateUserUseCaseImpl implements CreateUserUseCase {
    private final UserPersistencePort port;
    private final UserApplicationMapper mapper;
    private final MessageProvider messageProvider;
    private final PasswordService passwordService;

    @Override
    public void execute(CreateUserCommand command) {
        if (port.existsByEmail(command.email().value())) {
            throw new IllegalArgumentException(messageProvider.getMessage("user.email.unique"));
        }

        User user = mapper.toDomain(command);
        user.getUserValue().setPassword(passwordService.encode(user.getUserValue().getPassword()));
        user.setProfilePicture(ProfilePicture.defaultPicture());
        user.setEnabled(false);
        // No se pondrá rol por el momento

        port.save(user);
    }
}
