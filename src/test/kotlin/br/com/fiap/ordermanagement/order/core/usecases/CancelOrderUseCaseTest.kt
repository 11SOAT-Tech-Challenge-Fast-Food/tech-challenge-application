package br.com.fiap.ordermanagement.order.core.usecases

import br.com.fiap.ordermanagement.order.core.entities.OrderEntity
import br.com.fiap.ordermanagement.order.core.entities.OrderItemEntity
import br.com.fiap.ordermanagement.order.core.entities.enums.OrderStatus
import br.com.fiap.ordermanagement.order.core.gateways.IOrderGateway
import br.com.fiap.ordermanagement.product.core.entities.ProductEntity
import br.com.fiap.ordermanagement.product.core.entities.enums.CategoryEnum
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

class CancelOrderUseCaseTest {

    private var orderGateway: IOrderGateway = mockk(relaxed = false)
    private var cancelOrderUseCase: CancelOrderUseCase = CancelOrderUseCase(orderGateway)

    @Test
    fun `should cancel order and restore product quantities`() {
        val product1 = ProductEntity(
            id = UUID.randomUUID(),
            name = "Product 1",
            category = CategoryEnum.BURGUER,
            price = BigDecimal("19.99"),
            amount = 5,
            description = "description"
        )

        val product2 = ProductEntity(
            id = UUID.randomUUID(),
            name = "Product 2",
            category = CategoryEnum.DRINK,
            price = BigDecimal("7.50"),
            amount = 10,
            description = "description"
        )

        val order = OrderEntity(
            id = UUID.randomUUID(),
            status = OrderStatus.CREATED,
            items = listOf(
                OrderItemEntity(
                    product = product1
                ),
                OrderItemEntity(
                    product = product2
                )
            ),
            createdAt = LocalDateTime.now()
        )

        every { orderGateway.findById(any()) } returns order
        every { orderGateway.update(any()) } just Runs
        every { orderGateway.updateProduct(any()) } just Runs

        cancelOrderUseCase.execute(order.id)

        verify(exactly = 1) { orderGateway.findById(any()) }

        verify(exactly = 1) { orderGateway.update(any()) }

        verify(exactly = 2) { orderGateway.updateProduct(any()) }

        verify { orderGateway.updateProduct(any()) }
    }

    @Test
    fun `should throw exception when order not found`() {
        val nonExistentOrderId = UUID.randomUUID()

        every { orderGateway.findById(nonExistentOrderId) } throws NoSuchElementException("Order not found")

        assertThrows<NoSuchElementException> {
            cancelOrderUseCase.execute(nonExistentOrderId)
        }

        verify(exactly = 1) { orderGateway.findById(nonExistentOrderId) }
        verify(exactly = 0) { orderGateway.update(any()) }
        verify(exactly = 0) { orderGateway.updateProduct(any()) }
    }
}
