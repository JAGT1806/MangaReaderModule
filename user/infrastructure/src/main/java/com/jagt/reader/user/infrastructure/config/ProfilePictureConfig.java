package com.jagt.reader.user.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataSize;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "app.profile-picture")
public class ProfilePictureConfig {
    private DataSize maxSize;
    private List<String> allowedTypes;
}
