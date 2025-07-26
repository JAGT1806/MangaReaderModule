package com.jagt.reader.user.application.command;

import com.jagt.reader.shared.common.domain.model.value.IDValue;
import org.springframework.web.multipart.MultipartFile;

public record UpdateUserProfilePictureCommand(
        IDValue userId,
        MultipartFile file
) {
}
