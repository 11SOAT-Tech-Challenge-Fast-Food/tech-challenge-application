package br.com.fiap.ordermanagement.order.common.mapper

import br.com.fiap.ordermanagement.customer.common.mapper.CustomerMapper.toDto
import br.com.fiap.ordermanagement.customer.common.mapper.CustomerMapper.toEntity
import br.com.fiap.ordermanagement.customer.common.mapper.CustomerMapper.toJpa
import br.com.fiap.ordermanagement.order.common.dtos.OrderDTO
import br.com.fiap.ordermanagement.order.common.dtos.OrderItemDTO
import br.com.fiap.ordermanagement.order.core.entities.OrderEntity
import br.com.fiap.ordermanagement.order.core.entities.OrderItemEntity
import br.com.fiap.ordermanagement.order.external.persistence.jpa.JpaOrder
import br.com.fiap.ordermanagement.order.external.persistence.jpa.JpaOrderItem
import br.com.fiap.ordermanagement.product.common.mapper.ProductMapper.toDTO
import br.com.fiap.ordermanagement.product.common.mapper.ProductMapper.toEntity
import br.com.fiap.ordermanagement.product.common.mapper.ProductMapper.toJpa
import java.time.LocalDateTime

object OrderMapper {
    fun OrderDTO.toEntity() = OrderEntity(
        id = id!!,
        status = status,
        items = items.map { OrderItemEntity(id = it.id, product = it.product.toEntity()) },
        customer = customer?.toEntity(),
        createdAt = createdAt!!,
        updatedAt = updatedAt!!
    )

    fun OrderEntity.toDto(
        updatedAt: LocalDateTime = this.updatedAt
    ) = OrderDTO(
        id = this.id,
        status = this.status,
        items = this.items.map { it.toDto() },
        customer = this.customer?.toDto(),
        createdAt = this.createdAt,
        updatedAt = updatedAt
    )

    fun OrderItemEntity.toDto() = OrderItemDTO(
        id = this.id,
        product = this.product.toDTO()
    )

    fun JpaOrder.toDto() = OrderDTO(
        id = this.id,
        status = this.status,
        items = this.items.map { it.toDto() },
        customer = this.customer?.toDto(),
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )

    fun JpaOrderItem.toDto() = OrderItemDTO(
        id = this.id,
        product = this.product.toDTO()
    )

    fun OrderDTO.toJpaOrder() = JpaOrder(
        id = this.id!!,
        status = this.status,
        items = emptyList(),
        createdAt = this.createdAt!!,
        updatedAt = this.updatedAt!!,
        customer = this.customer?.toJpa()
    )

    fun OrderItemDTO.toJpaOrderItem(jpaOrder: JpaOrder) = JpaOrderItem(
        id = this.id,
        order = jpaOrder,
        product = this.product.toJpa()
    )
}
