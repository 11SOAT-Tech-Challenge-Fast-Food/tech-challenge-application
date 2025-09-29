package br.com.fiap.ordermanagement.order.core.gateways

import br.com.fiap.ordermanagement.customer.core.entities.CustomerEntity
import br.com.fiap.ordermanagement.order.core.entities.OrderEntity
import br.com.fiap.ordermanagement.order.core.entities.enums.OrderStatus
import br.com.fiap.ordermanagement.product.core.entities.ProductEntity
import java.util.*

interface IOrderGateway {

    fun save(order: OrderEntity)
    fun findById(id: UUID): OrderEntity?
    fun update(order: OrderEntity)
    fun findAll(): List<OrderEntity>
    fun findAllByStatus(status: OrderStatus): List<OrderEntity>
    fun findProductById(id: UUID): ProductEntity
    fun updateProduct(product: ProductEntity)
    fun findCustomerByCustomerId(id: UUID): CustomerEntity
}
