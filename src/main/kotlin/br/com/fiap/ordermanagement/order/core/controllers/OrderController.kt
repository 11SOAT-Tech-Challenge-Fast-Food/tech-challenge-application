package br.com.fiap.ordermanagement.order.core.controllers

import br.com.fiap.ordermanagement.order.common.dtos.OrderDTO
import br.com.fiap.ordermanagement.order.common.interfaces.DataSource
import br.com.fiap.ordermanagement.order.core.entities.enums.OrderStatus
import br.com.fiap.ordermanagement.order.core.gateways.IOrderGateway
import br.com.fiap.ordermanagement.order.core.gateways.OrderGateway
import br.com.fiap.ordermanagement.order.core.presenters.OrderPresenter
import br.com.fiap.ordermanagement.order.core.usecases.CancelOrderUseCase
import br.com.fiap.ordermanagement.order.core.usecases.CreateOrderUseCase
import br.com.fiap.ordermanagement.order.core.usecases.FindAllOrdersUseCase
import br.com.fiap.ordermanagement.order.core.usecases.FindOrderByIdUseCase
import br.com.fiap.ordermanagement.order.core.usecases.UpdateOrderUseCase
import java.util.*

class OrderController(
    dataSource: DataSource
) {
    private val orderRepositoryGateway: IOrderGateway = OrderGateway(dataSource)

    fun createOrder(customerId: UUID?, items: List<UUID>): OrderDTO {
        val createOrder = CreateOrderUseCase(orderRepositoryGateway)

        val createdOrder = createOrder.execute(customerId, items)

        return OrderPresenter.orderToDto(createdOrder)
    }

    fun getAllOrders(): List<OrderDTO> {
        val findAllOrders = FindAllOrdersUseCase(orderRepositoryGateway)

        val orders = findAllOrders.execute()

        return orders.map { OrderPresenter.orderToDto(it) }
    }

    fun getOrderById(id: UUID): OrderDTO {
        val findOrder = FindOrderByIdUseCase(orderRepositoryGateway)

        return OrderPresenter.orderToDto(findOrder.execute(id))
    }

    fun getOrdersByStatus(status: OrderStatus): List<OrderDTO> {
        val findAllOrders = FindAllOrdersUseCase(orderRepositoryGateway)

        val orders = findAllOrders.execute(status)

        return orders.map { OrderPresenter.orderToDto(it) }
    }

    fun cancel(id: UUID) {
        val cancelOrder = CancelOrderUseCase(orderRepositoryGateway)

        cancelOrder.execute(id)
    }

    fun updateOrder(id: UUID, status: OrderStatus) {
        val updateOrder = UpdateOrderUseCase(orderRepositoryGateway)

        updateOrder.execute(status, id)
    }
}
