package br.com.fiap.ordermanagement.payment.external.config

import feign.RequestInterceptor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FeignConfig(
    @Value("\${mercado-pago.access-token}") private val accessToken: String
) {

    @Bean
    fun requestInterceptor(): RequestInterceptor {
        return RequestInterceptor { template ->
            template.header("Authorization", "Bearer $accessToken")
        }
    }
}
