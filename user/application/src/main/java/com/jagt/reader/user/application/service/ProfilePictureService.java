package com.jagt.reader.user.application.service;

import com.jagt.reader.user.application.port.input.ProfilePictureUseCase;
import com.jagt.reader.user.domain.exception.ProfilePictureAlreadyDefaultException;
import com.jagt.reader.user.domain.exception.ProfilePictureNotFoundException;
import com.jagt.reader.user.domain.exception.UserNotFoundException;
import com.jagt.reader.user.domain.model.User;
import com.jagt.reader.user.domain.model.value.ProfilePicture;
import com.jagt.reader.user.domain.port.output.FileStorageService;
import com.jagt.reader.user.domain.port.output.UserPersistencePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class ProfilePictureService implements ProfilePictureUseCase {
    private final UserPersistencePort port;
    private final FileStorageService fileStorageService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfilePictureService.class);

    @Override
    public ProfilePicture update(Long userId, byte[] imageData, String fileName, String newFileName, String contentType) {
        LOGGER.info("Updating profile picture");
        LOGGER.info("userId: {}", userId);
        LOGGER.info("fileName: {}", fileName);
        LOGGER.info("newFileName: {}", newFileName);
        LOGGER.info("contentType: {}", contentType);
        User user = getUser(userId);

        LOGGER.info("user: {}", user);
        ProfilePicture currentPicture = user.getProfilePicture();

        if (!currentPicture.isDefault() && fileName != null ) {
            fileStorageService.deleteFile(fileName);
        }

        String uniqueFileName = generateUniqueFileName(userId, newFileName);
        LOGGER.info("FileName: {}", uniqueFileName);

        String imageUrl = fileStorageService.uploadFile(imageData, uniqueFileName, contentType);
        LOGGER.info("imageUrl: {}", imageUrl);
        return ProfilePicture.customPicture(imageUrl, uniqueFileName);
    }

    @Override
    @Transactional
    public ProfilePicture delete(Long userId) {
        LOGGER.info("Deleting profile picture for user: {}", userId);

        User user = getUser(userId);
        ProfilePicture currentPicture = user.getProfilePicture();

        if (currentPicture.isDefault()) {
            throw new ProfilePictureAlreadyDefaultException("profile.picture.already.default");
        }

        if (currentPicture.getFileName() == null) {
            throw new ProfilePictureNotFoundException("profile.picture.filename.not.found");
        }

        fileStorageService.deleteFile(currentPicture.getFileName());
        LOGGER.info("Profile picture deleted successfully for user: {}", userId);

        return ProfilePicture.defaultPicture();
    }

    @Override
    public String get(Long userId) {
        LOGGER.info("Getting profile picture URL for user: {}", userId);

        User user = getUser(userId);
        ProfilePicture profilePicture = user.getProfilePicture();

        if (profilePicture.isDefault() || profilePicture.getFileName() == null) {
            throw new ProfilePictureNotFoundException("profile.picture.not.found");
        }

        return fileStorageService.generarePreSignedDownloadUrl(
                profilePicture.getFileName(),
                Duration.ofMinutes(10)
        );
    }

    private String generateUniqueFileName(Long userId, String originalFileName) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String extension = getFileExtension(originalFileName);
        return "profile-pictures/" + userId + "-" + timestamp + extension;
    }

    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }


    private User getUser(Long userID) {
        return port.findById(userID)
                .orElseThrow(() -> new UserNotFoundException(String.valueOf(userID)));
    }
}
