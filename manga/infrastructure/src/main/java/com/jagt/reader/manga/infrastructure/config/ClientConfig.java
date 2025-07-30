package com.jagt.reader.manga.infrastructure.config;

import com.jagt.reader.manga.infrastructure.output.api.client.MangaDexClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackageClasses = MangaDexClient.class)
public class ClientConfig {
}
