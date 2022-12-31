package com.binar.flyket.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class OpenApiConfig {


    @Bean
    public OpenAPI bioskopOpenAPI(@Value("${app.description}") String desc,
                                  @Value("${app.version}") String appVersion,
                                  @Value("${app.name}") String title) {

        return new OpenAPI()
                .info(new Info()
                        .title(title)
                        .version(appVersion)
                        .description(desc))
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .addSecurityItem(
                        new SecurityRequirement()
                                .addList("bearer-jwt", Arrays.asList("read", "write"))
                                .addList("bearer-key", Collections.emptyList())
                )
                .addServersItem(
                        new Server().url("https://flyket-app.herokuapp.com/")
//                        new Server().url("https://api-flyket.up.railway.app/")
//                        new Server().url("http://localhost:8080/")
                );


    }
}
