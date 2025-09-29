package br.com.fiap.ordermanagement.order.core.usecases

import br.com.fiap.ordermanagement.order.core.entities.OrderEntity
import br.com.fiap.ordermanagement.order.core.entities.enums.OrderStatus
import br.com.fiap.ordermanagement.order.core.gateways.IOrderGateway

@Suppress("MagicNumber")
class FindAllOrdersUseCase(
    private val orderRepository: IOrderGateway
) {
    fun execute(status: OrderStatus? = null): List<OrderEntity> {
        val orders = when (status) {
            null -> orderRepository.findAll()
            else -> orderRepository.findAllByStatus(status)
        }

        return orders.sortedWith(
            compareBy<OrderEntity> {
                when (it.status) {
                    OrderStatus.READY -> 1
                    OrderStatus.IN_PREPARATION -> 2
                    OrderStatus.RECEIVED -> 3
                    OrderStatus.PAID -> 4
                    OrderStatus.CREATED -> 5
                    else -> 6
                }
            }.thenBy { it.createdAt }
        ).filter { it.status != OrderStatus.COMPLETED && it.status != OrderStatus.CANCELED }
    }
}
