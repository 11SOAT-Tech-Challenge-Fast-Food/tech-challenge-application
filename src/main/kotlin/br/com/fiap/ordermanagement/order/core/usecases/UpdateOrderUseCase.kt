package br.com.fiap.ordermanagement.order.core.usecases

import br.com.fiap.ordermanagement.order.core.entities.OrderEntity
import br.com.fiap.ordermanagement.order.core.entities.enums.OrderStatus
import br.com.fiap.ordermanagement.order.core.entities.validate
import br.com.fiap.ordermanagement.order.core.gateways.IOrderGateway
import br.com.fiap.ordermanagement.order.core.gateways.exception.NotFoundOrderException
import java.util.*

class UpdateOrderUseCase(
    private val orderRepository: IOrderGateway
) {
    fun execute(status: OrderStatus, id: UUID) {
        val order = orderRepository.findById(id) ?: throw NotFoundOrderException("Order not found")

        order.validate(status)

        val updatedOrder = order.copy(status = status)

        orderRepository.update(updatedOrder)

        if (status == OrderStatus.PAID) {
            sendToPreparation(updatedOrder)
        }
    }

    private fun sendToPreparation(order: OrderEntity) {
        orderRepository.update(order.copy(status = OrderStatus.RECEIVED))
    }
}
