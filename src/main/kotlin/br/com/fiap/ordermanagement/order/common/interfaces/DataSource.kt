package br.com.fiap.ordermanagement.order.common.interfaces

import br.com.fiap.ordermanagement.customer.common.dtos.CustomerDTO
import br.com.fiap.ordermanagement.order.common.dtos.OrderDTO
import br.com.fiap.ordermanagement.order.core.entities.enums.OrderStatus
import br.com.fiap.ordermanagement.product.common.dtos.ProductDTO
import java.util.*

interface DataSource {
    fun save(order: OrderDTO)
    fun findById(id: UUID): OrderDTO?
    fun findAll(): List<OrderDTO>
    fun findAllByStatus(status: OrderStatus): List<OrderDTO>
    fun update(order: OrderDTO)
    fun findProductById(id: UUID): ProductDTO?
    fun updateProduct(product: ProductDTO)
    fun findCustomerById(id: UUID): CustomerDTO?
}
