package br.com.fiap.ordermanagement.order.core.usecases

import br.com.fiap.ordermanagement.order.core.entities.enums.OrderStatus
import br.com.fiap.ordermanagement.order.core.entities.validate
import br.com.fiap.ordermanagement.order.core.gateways.IOrderGateway
import br.com.fiap.ordermanagement.order.core.gateways.exception.NotFoundOrderException
import java.util.*

class CancelOrderUseCase(
    private val orderRepository: IOrderGateway
) {
    fun execute(id: UUID) {
        val order = orderRepository.findById(id) ?: throw NotFoundOrderException("Order not found")

        order.validate(OrderStatus.CANCELED)

        orderRepository.update(order.copy(status = OrderStatus.CANCELED))

        order.items.forEach { orderItem ->
            val product = orderItem.product
            val updatedProduct = product.copy(amount = product.amount + 1)
            orderRepository.updateProduct(updatedProduct)
        }
    }
}
