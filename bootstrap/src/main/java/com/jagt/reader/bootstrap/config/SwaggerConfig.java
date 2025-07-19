package com.jagt.reader.bootstrap.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss 'Z'";

    static {
        StringSchema dateTimeSchema = new StringSchema();
        dateTimeSchema.setFormat(DATE_TIME_FORMAT);
        dateTimeSchema.example(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
        SpringDocUtils.getConfig().replaceWithSchema(LocalDateTime.class, dateTimeSchema);

    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Manga Reader API")
                        .version("0.1.0")
                        .description("Manga Reader API docs")
                );
    }

    @Bean
    public OpenApiCustomizer languageHeaderCustomizer() {
        return openApi -> openApi.getPaths().values().forEach(pathItem -> pathItem
                .readOperations()
                .forEach(operation -> {
                    Parameter langHeader = new Parameter()
                            .name("Accept-Language")
                            .description("Idioma preferido para la respuesta de la aplicación (es, en, fr)")
                            .required(false)
                            .schema(new StringSchema()._default("es"));
                    operation.addParametersItem(langHeader);
                })
        );
    }

}
