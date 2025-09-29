package br.com.fiap.ordermanagement.order.external.persistence

import br.com.fiap.ordermanagement.customer.common.dtos.CustomerDTO
import br.com.fiap.ordermanagement.customer.common.mapper.CustomerMapper.toDto
import br.com.fiap.ordermanagement.customer.external.persistence.jpa.CustomerJpaRepository
import br.com.fiap.ordermanagement.order.common.dtos.OrderDTO
import br.com.fiap.ordermanagement.order.common.interfaces.DataSource
import br.com.fiap.ordermanagement.order.common.mapper.OrderMapper.toDto
import br.com.fiap.ordermanagement.order.common.mapper.OrderMapper.toJpaOrder
import br.com.fiap.ordermanagement.order.common.mapper.OrderMapper.toJpaOrderItem
import br.com.fiap.ordermanagement.order.core.entities.enums.OrderStatus
import br.com.fiap.ordermanagement.order.external.persistence.jpa.OrderJpaRepository
import br.com.fiap.ordermanagement.product.common.dtos.ProductDTO
import br.com.fiap.ordermanagement.product.common.mapper.ProductMapper.toDTO
import br.com.fiap.ordermanagement.product.common.mapper.ProductMapper.toJpa
import br.com.fiap.ordermanagement.product.external.persistence.jpa.ProductJpaRepository
import org.springframework.stereotype.Component
import java.util.*

@Component("orderDataSource")
class DataSourceImpl(
    private val orderJpaRepository: OrderJpaRepository,
    private val productJpaRepository: ProductJpaRepository,
    private val customerJpaRepository: CustomerJpaRepository
) : DataSource {

    override fun save(order: OrderDTO) {
        val jpaOrder = order.toJpaOrder().apply {
            items = order.items.map { it.toJpaOrderItem(this) }
        }

        orderJpaRepository.save(jpaOrder)
    }

    override fun findById(id: UUID): OrderDTO? {
        val order = orderJpaRepository.findById(id)

        if (order.isEmpty) {
            return null
        }

        return order.get().toDto()
    }

    override fun findAll(): List<OrderDTO> {
        val orders = orderJpaRepository.findAll()

        return orders.map {
            it.toDto()
        }
    }

    override fun findAllByStatus(status: OrderStatus): List<OrderDTO> {
        val orders = orderJpaRepository.findAllByStatus(status)

        return orders.map {
            it.toDto()
        }
    }

    override fun update(order: OrderDTO) {
        val jpaOrder = order.toJpaOrder()
        jpaOrder.items = order.items.map { it.toJpaOrderItem(jpaOrder) }

        orderJpaRepository.save(jpaOrder)
    }

    override fun findProductById(id: UUID): ProductDTO? {
        val product = productJpaRepository.findById(id)

        if (product.isEmpty) {
            return null
        }

        return product.get().toDTO()
    }

    override fun updateProduct(product: ProductDTO) {
        val jpaProduct = product.toJpa()

        productJpaRepository.save(jpaProduct)
    }

    override fun findCustomerById(id: UUID): CustomerDTO? {
        val customer = customerJpaRepository.findById(id)

        if (customer.isEmpty) {
            return null
        }

        return customer.get().toDto()
    }
}
