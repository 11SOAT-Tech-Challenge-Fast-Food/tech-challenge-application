package br.com.fiap.ordermanagement.order.common.dtos

import br.com.fiap.ordermanagement.customer.common.dtos.CustomerDTO
import br.com.fiap.ordermanagement.order.core.entities.enums.OrderStatus
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.LocalDateTime
import java.util.*

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class OrderDTO(
    val id: UUID?,
    val status: OrderStatus,
    val items: List<OrderItemDTO>,
    val customer: CustomerDTO? = null,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
)
