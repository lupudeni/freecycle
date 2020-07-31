package com.denisalupu.freecycle.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "FreeCycle",
                description = "FreeCycle is an app that is used for donating objects. The purpose of the app is to centralise and organise the transactions between donor and requester and serves a platform for establishing connection between people.",
                license = @License(name = "Apache Licence 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"),
                version = "1.0"
        )
)
@SecurityScheme(
        name = "http_basic",
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
public class OpenAPIConfig {

}
