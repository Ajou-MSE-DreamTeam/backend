package ajou.mse.dimensionguard.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI eateryApi() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Dimension Guard API Docs")
                                .description("2023-1 Ajou University media software engineering project API specification")
                                .version("0.0.1")
                )
                .externalDocs(
                        new ExternalDocumentation()
                                .description("Github organization of team dream-team")
                                .url("https://github.com/Ajou-MSE-DreamTeam")
                )
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        "access-token",
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("Bearer")
                                                .bearerFormat("JWT")
                                )
                );
    }
}
