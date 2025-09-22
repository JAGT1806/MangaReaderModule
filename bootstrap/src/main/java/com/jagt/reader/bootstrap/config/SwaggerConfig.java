package com.jagt.reader.bootstrap.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
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
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
@SecurityScheme(
        name = "cookieAuth",
        type = SecuritySchemeType.APIKEY,
        in = SecuritySchemeIn.COOKIE,
        paramName = "refreshToken"
)
public class SwaggerConfig {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

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
                            .in("header")
                            .schema(new StringSchema()
                                    ._default("es")
                                    .addEnumItem("es")
                                    .addEnumItem("en")
                                    .addEnumItem("fr")
                            );
                    operation.addParametersItem(langHeader);
                })
        );
    }

}
