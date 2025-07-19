package com.jagt.reader.bootstrap.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = {
        "com.jagt.reader.*.*.infrastructure",
        "com.jagt.reader.*.infrastructure",
        "com.jagt.reader.*.application"
})
@EnableJpaRepositories(basePackages = {
        "com.jagt.reader.*.infrastructure.output.persistence.repository"
})
@EntityScan(basePackages = {
        "com.jagt.reader.*.infrastructure.output.persistence.entity"
})
public class ApplicationBean {
}
