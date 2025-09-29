package br.com.fiap.ordermanagement.order.core.presenters

import br.com.fiap.ordermanagement.customer.common.mapper.CustomerMapper.toDto
import br.com.fiap.ordermanagement.order.common.dtos.OrderDTO
import br.com.fiap.ordermanagement.order.common.mapper.OrderMapper.toDto
import br.com.fiap.ordermanagement.order.core.entities.OrderEntity

object OrderPresenter {
    fun orderToDto(order: OrderEntity) = OrderDTO(
        id = order.id,
        status = order.status,
        items = order.items.map { it.toDto() },
        customer = order.customer?.toDto(),
        createdAt = order.createdAt,
        updatedAt = order.createdAt
    )
}
