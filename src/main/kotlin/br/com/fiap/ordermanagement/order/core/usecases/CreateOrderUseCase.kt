package br.com.fiap.ordermanagement.order.core.usecases

import br.com.fiap.ordermanagement.order.core.entities.OrderEntity
import br.com.fiap.ordermanagement.order.core.entities.OrderItemEntity
import br.com.fiap.ordermanagement.order.core.gateways.IOrderGateway
import java.util.*

class CreateOrderUseCase(
    private val orderRepository: IOrderGateway
) {
    fun execute(customerId: UUID?, items: List<UUID>): OrderEntity {
        val customer = customerId?.let {
            orderRepository.findCustomerByCustomerId(it)
        }

        val products = items.map { productId ->
            val product = orderRepository.findProductById(productId)

            require(product.amount > 0) { "Product is out of stock" }
            product
        }

        val orderItems = products.map { product ->
            OrderItemEntity(product = product)
        }

        val newOrder = OrderEntity(items = orderItems, customer = customer)

        orderRepository.save(newOrder)

        products.forEach { product ->
            val updatedProduct = product.copy(amount = product.amount - 1)
            orderRepository.updateProduct(updatedProduct)
        }

        return newOrder
    }
}
