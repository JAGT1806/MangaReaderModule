package com.jagt.reader.user.application.usecase;

import com.jagt.reader.user.application.command.UpdateUserProfilePictureCommand;
import com.jagt.reader.user.application.port.input.GetUserUseCase;
import com.jagt.reader.user.application.port.input.ProfilePictureUseCase;
import com.jagt.reader.user.application.port.input.UpdateUserUseCase;
import com.jagt.reader.user.domain.model.User;
import com.jagt.reader.user.domain.model.value.ProfilePicture;
import com.jagt.reader.user.domain.port.output.UserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {
    private final UserPersistencePort userPersistencePort;
    private final GetUserUseCase getUserUseCase;
    private final ProfilePictureUseCase profilePictureUseCase;

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateUserUseCaseImpl.class);

    @Override
    public void execute() {

    }

    @Override
    public void execute(UpdateUserProfilePictureCommand command) throws IOException {
        LOGGER.info("Update user profile picture");
        LOGGER.info("Command: {}", command);
        User user = getUserUseCase.execute(command.userId().value());

        LOGGER.info("User: {}", user);
        ProfilePicture profilePicture = profilePictureUseCase.update(command.userId().value(), command.file().getBytes(), user.getProfilePicture().getFileName(), command.file().getOriginalFilename(), command.file().getContentType());

        user.setProfilePicture(profilePicture);
        user.setAuditTimestamps(user.getAuditTimestamps().updated());

        LOGGER.info("User Updated: {}", user);
        LOGGER.info("Updated user profile picture");
        userPersistencePort.save(user);
    }
}
