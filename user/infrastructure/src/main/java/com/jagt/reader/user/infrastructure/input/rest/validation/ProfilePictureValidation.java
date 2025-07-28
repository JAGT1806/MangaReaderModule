package com.jagt.reader.user.infrastructure.input.rest.validation;

import com.jagt.reader.user.domain.exception.ProfilePictureValidationException;
import com.jagt.reader.user.infrastructure.config.ProfilePictureConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class ProfilePictureValidation {
    private final ProfilePictureConfig config;

    public void validate(MultipartFile file) {
        validateNotEmpty(file);
        validateSize(file);
        validateContentType(file);
    }

    private void validateNotEmpty(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ProfilePictureValidationException("file.image.empty");
        }
    }

    private void validateSize(MultipartFile file) {
        if (file.getSize() > config.getMaxSize().toBytes()) {
            throw new ProfilePictureValidationException("file.image.size.exceeded");
        }
    }

    private void validateContentType(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null || !config.getAllowedTypes().contains(contentType)) {
            throw new ProfilePictureValidationException("file.image.type.not.allowed");
        }
    }

}
