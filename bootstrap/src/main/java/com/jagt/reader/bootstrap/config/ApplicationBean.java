package com.jagt.reader.bootstrap.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "com.jagt.reader.*.*.infrastructure"

})
public class ApplicationBean {
}
