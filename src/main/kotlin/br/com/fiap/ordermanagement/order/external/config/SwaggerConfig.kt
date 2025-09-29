package br.com.fiap.ordermanagement.order.external.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Order Management")
                    .version("1.0.0")
                    .description("This is Order Management API documented with Swagger and SpringDoc.")
            )
    }
}
