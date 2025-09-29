package br.com.fiap.ordermanagement.product.core.entities

import br.com.fiap.ordermanagement.product.core.entities.enums.CategoryEnum
import java.math.BigDecimal
import java.util.*

data class ProductEntity(
    val id: UUID,
    val name: String,
    val description: String?,
    val category: CategoryEnum,
    val price: BigDecimal,
    val amount: Int
)
