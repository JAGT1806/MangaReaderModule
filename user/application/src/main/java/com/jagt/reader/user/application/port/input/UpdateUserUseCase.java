package com.jagt.reader.user.application.port.input;

import com.jagt.reader.user.application.command.UpdateUserProfilePictureCommand;

import java.io.IOException;

public interface UpdateUserUseCase {
    void execute();

    void execute(UpdateUserProfilePictureCommand command) throws IOException;
}
