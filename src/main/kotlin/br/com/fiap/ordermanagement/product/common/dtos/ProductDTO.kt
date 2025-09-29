package br.com.fiap.ordermanagement.product.common.dtos

import br.com.fiap.ordermanagement.product.core.entities.enums.CategoryEnum
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.math.BigDecimal
import java.util.*

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ProductDTO(
    val id: UUID? = null,
    val name: String,
    val description: String? = "",
    val category: CategoryEnum,
    val price: BigDecimal,
    val amount: Int
)
