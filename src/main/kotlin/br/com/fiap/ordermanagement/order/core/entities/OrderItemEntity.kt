package br.com.fiap.ordermanagement.order.core.entities

import br.com.fiap.ordermanagement.product.core.entities.ProductEntity
import java.util.*

data class OrderItemEntity(
    val id: UUID = UUID.randomUUID(),
    val product: ProductEntity
)
