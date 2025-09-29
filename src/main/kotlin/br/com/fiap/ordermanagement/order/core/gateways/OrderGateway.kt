package br.com.fiap.ordermanagement.order.core.gateways

import br.com.fiap.ordermanagement.customer.common.mapper.CustomerMapper.toEntity
import br.com.fiap.ordermanagement.customer.core.entities.CustomerEntity
import br.com.fiap.ordermanagement.order.common.interfaces.DataSource
import br.com.fiap.ordermanagement.order.common.mapper.OrderMapper.toDto
import br.com.fiap.ordermanagement.order.common.mapper.OrderMapper.toEntity
import br.com.fiap.ordermanagement.order.core.entities.OrderEntity
import br.com.fiap.ordermanagement.order.core.entities.enums.OrderStatus
import br.com.fiap.ordermanagement.order.core.gateways.exception.NotFoundOrderException
import br.com.fiap.ordermanagement.product.common.mapper.ProductMapper.toDTO
import br.com.fiap.ordermanagement.product.common.mapper.ProductMapper.toEntity
import br.com.fiap.ordermanagement.product.core.entities.ProductEntity
import java.time.LocalDateTime
import java.util.*

class OrderGateway(
    private val dataSource: DataSource
) : IOrderGateway {
    override fun save(order: OrderEntity) {
        val orderDTO = order.toDto()

        dataSource.save(orderDTO)
    }

    override fun update(order: OrderEntity) {
        val orderDTO = order.toDto(updatedAt = LocalDateTime.now())

        dataSource.update(orderDTO)
    }

    override fun findById(id: UUID): OrderEntity? {
        val order = dataSource.findById(id) ?: return null

        return order.toEntity()
    }

    override fun findAll(): List<OrderEntity> {
        val orders = dataSource.findAll()

        return orders.map {
            it.toEntity()
        }
    }

    override fun findAllByStatus(status: OrderStatus): List<OrderEntity> {
        val orders = dataSource.findAllByStatus(status)

        return orders.map {
            it.toEntity()
        }
    }

    override fun findProductById(id: UUID): ProductEntity {
        val product = dataSource.findProductById(id) ?: throw NotFoundOrderException("Product not found")

        return product.toEntity()
    }

    override fun updateProduct(product: ProductEntity) {
        dataSource.updateProduct(product.toDTO())
    }

    override fun findCustomerByCustomerId(id: UUID): CustomerEntity {
        val customer = dataSource.findCustomerById(id) ?: throw NotFoundOrderException("Customer not found")

        return customer.toEntity()
    }
}
