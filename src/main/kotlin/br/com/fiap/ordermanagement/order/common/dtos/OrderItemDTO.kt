package br.com.fiap.ordermanagement.order.common.dtos

import br.com.fiap.ordermanagement.product.common.dtos.ProductDTO
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.util.*

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class OrderItemDTO(
    val id: UUID,
    val product: ProductDTO
)
