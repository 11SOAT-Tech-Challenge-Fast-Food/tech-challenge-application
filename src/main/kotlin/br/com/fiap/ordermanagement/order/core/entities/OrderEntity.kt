package br.com.fiap.ordermanagement.order.core.entities

import br.com.fiap.ordermanagement.customer.core.entities.CustomerEntity
import br.com.fiap.ordermanagement.order.core.entities.enums.OrderStatus
import br.com.fiap.ordermanagement.order.core.entities.enums.OrderStatus.CREATED
import br.com.fiap.ordermanagement.order.core.gateways.exception.InvalidUpdateException
import java.time.LocalDateTime
import java.util.*

data class OrderEntity(
    val id: UUID = UUID.randomUUID(),
    val status: OrderStatus = CREATED,
    var items: List<OrderItemEntity> = emptyList(),
    val customer: CustomerEntity? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)

fun OrderEntity.validate(newStatus: OrderStatus) {
    if (!newStatus.validateChange(status)) {
        throw InvalidUpdateException("Cannot change status, order is not on the right step")
    }
}
