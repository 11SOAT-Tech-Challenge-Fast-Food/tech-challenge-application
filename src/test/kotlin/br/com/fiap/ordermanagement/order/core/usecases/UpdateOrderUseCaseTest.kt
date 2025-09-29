package br.com.fiap.ordermanagement.order.core.usecases

import br.com.fiap.ordermanagement.order.core.entities.OrderEntity
import br.com.fiap.ordermanagement.order.core.entities.OrderItemEntity
import br.com.fiap.ordermanagement.order.core.entities.enums.OrderStatus
import br.com.fiap.ordermanagement.order.core.gateways.IOrderGateway
import br.com.fiap.ordermanagement.order.core.gateways.exception.InvalidUpdateException
import br.com.fiap.ordermanagement.product.core.entities.ProductEntity
import br.com.fiap.ordermanagement.product.core.entities.enums.CategoryEnum
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

class UpdateOrderUseCaseTest {

    private var orderGateway: IOrderGateway = mockk(relaxed = false)
    private var updateOrderUseCase: UpdateOrderUseCase = UpdateOrderUseCase(orderGateway)

    @Test
    fun `should update order status successfully`() {
        val product = ProductEntity(
            id = UUID.randomUUID(),
            name = "Test Product",
            category = CategoryEnum.DRINK,
            price = BigDecimal("19.99"),
            amount = 10,
            description = "description"
        )

        val order = OrderEntity(
            id = UUID.randomUUID(),
            status = OrderStatus.RECEIVED,
            items = listOf(
                OrderItemEntity(
                    product = product
                )
            ),
            createdAt = LocalDateTime.now()
        )
        val newStatus = OrderStatus.IN_PREPARATION

        every { orderGateway.findById(any()) } returns order
        every { orderGateway.update(any()) } returnsArgument 0

        updateOrderUseCase.execute(newStatus, order.id)

        verify(exactly = 1) { orderGateway.findById(any()) }
        verify(exactly = 1) { orderGateway.update(any()) }
    }

    @Test
    fun `should send to preparation when status is PAID`() {
        val product = ProductEntity(
            id = UUID.randomUUID(),
            name = "Test Product",
            category = CategoryEnum.DRINK,
            price = BigDecimal("19.99"),
            amount = 10,
            description = "description"
        )

        val order = OrderEntity(
            id = UUID.randomUUID(),
            status = OrderStatus.CREATED,
            items = listOf(
                OrderItemEntity(
                    product = product
                )
            ),
            createdAt = LocalDateTime.now()
        )

        every { orderGateway.findById(any()) } returns order
        every { orderGateway.update(any()) } returnsArgument 0

        updateOrderUseCase.execute(OrderStatus.PAID, order.id)

        verify(exactly = 2) { orderGateway.update(any()) }

        verifySequence {
            orderGateway.findById(any())
            orderGateway.update(match { it.status == OrderStatus.PAID })
            orderGateway.update(match { it.status == OrderStatus.RECEIVED })
        }
    }

    @Test
    fun `should throw exception when order not found`() {
        every { orderGateway.findById(any()) } throws NoSuchElementException("Order not found")

        assertThrows<NoSuchElementException> {
            updateOrderUseCase.execute(OrderStatus.IN_PREPARATION, UUID.randomUUID())
        }

        verify(exactly = 1) { orderGateway.findById(any()) }
        verify(exactly = 0) { orderGateway.update(any()) }
    }

    @Test
    fun `should validate status transition`() {
        val product = ProductEntity(
            id = UUID.randomUUID(),
            name = "Test Product",
            category = CategoryEnum.DRINK,
            price = BigDecimal("19.99"),
            amount = 10,
            description = "description"
        )

        val order = OrderEntity(
            id = UUID.randomUUID(),
            customer = null,
            status = OrderStatus.COMPLETED,
            items = listOf(
                OrderItemEntity(
                    product = product
                )
            ),
            createdAt = LocalDateTime.now()
        )

        every { orderGateway.findById(any()) } returns order

        assertThrows<InvalidUpdateException> {
            updateOrderUseCase.execute(OrderStatus.IN_PREPARATION, order.id)
        }

        verify(exactly = 1) { orderGateway.findById(any()) }
        verify(exactly = 0) { orderGateway.update(any()) }
    }
}
