package br.com.fiap.ordermanagement.customer.common.dtos

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.util.UUID

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CustomerDTO(
    val id: UUID? = null,
    val name: String,
    val email: String? = null,
    val cpf: String? = null
)
