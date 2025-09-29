package br.com.fiap.ordermanagement.order.common.dtos

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.util.*

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CreateOrderDTO(
    val products: List<UUID>,
    val customerId: UUID?
)
