package org.example.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
  @Bean
  public OpenAPI userApi() {
    return new OpenAPI()
        .info(
            new Info()
                .title("ProPill API")
                .description("Описание сервиса онлайн аптеки")
                .version("1.0.0"));
  }
}
