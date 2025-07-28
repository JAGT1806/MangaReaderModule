package com.jagt.reader.user.application.port.input;

import com.jagt.reader.user.domain.model.value.ProfilePicture;

public interface ProfilePictureUseCase {
    ProfilePicture update(Long userId, byte[] imageData, String fileName, String newFileName, String contentType);
    ProfilePicture delete(Long userId);
    String get(Long userId);

}
