package br.com.fiap.ordermanagement.order.common.dtos

import br.com.fiap.ordermanagement.order.core.entities.enums.OrderStatus

data class UpdateOrderDTO(
    val status: OrderStatus
)
