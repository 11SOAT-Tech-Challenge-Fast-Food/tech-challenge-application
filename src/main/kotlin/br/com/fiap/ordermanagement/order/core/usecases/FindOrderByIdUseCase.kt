package br.com.fiap.ordermanagement.order.core.usecases

import br.com.fiap.ordermanagement.order.core.entities.OrderEntity
import br.com.fiap.ordermanagement.order.core.gateways.IOrderGateway
import br.com.fiap.ordermanagement.order.core.gateways.exception.NotFoundOrderException
import java.util.*

class FindOrderByIdUseCase(
    private val orderRepository: IOrderGateway
) {
    fun execute(id: UUID): OrderEntity {
        return orderRepository.findById(id) ?: throw NotFoundOrderException("Order not found")
    }
}
