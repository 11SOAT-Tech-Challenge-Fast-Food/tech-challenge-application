package br.com.fiap.ordermanagement.order.core.usecases

import br.com.fiap.ordermanagement.customer.core.entities.CustomerEntity
import br.com.fiap.ordermanagement.order.core.entities.OrderEntity
import br.com.fiap.ordermanagement.order.core.entities.OrderItemEntity
import br.com.fiap.ordermanagement.order.core.entities.enums.OrderStatus
import br.com.fiap.ordermanagement.order.core.gateways.IOrderGateway
import br.com.fiap.ordermanagement.product.core.entities.ProductEntity
import br.com.fiap.ordermanagement.product.core.entities.enums.CategoryEnum
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

class FindAllOrdersUseCaseTest {

    private var orderGateway: IOrderGateway = mockk(relaxed = false)
    private var findAllOrdersUseCase: FindAllOrdersUseCase = FindAllOrdersUseCase(orderGateway)

    @Test
    fun `should return all orders sorted by status priority when no status is provided`() {
        val customer = CustomerEntity(
            id = UUID.randomUUID(),
            name = "John Doe",
            email = "john@example.com",
            cpf = "12345678900"
        )

        val product = ProductEntity(
            id = UUID.randomUUID(),
            name = "Product 1",
            category = CategoryEnum.BURGUER,
            price = BigDecimal("19.99"),
            amount = 10,
            description = "description"
        )

        val orders = listOf(
            OrderEntity(
                id = UUID.randomUUID(),
                customer = customer,
                status = OrderStatus.IN_PREPARATION,
                items = listOf(
                    OrderItemEntity(
                        product = product
                    )
                ),
                createdAt = LocalDateTime.now()
            ),
            OrderEntity(
                id = UUID.randomUUID(),
                customer = null,
                status = OrderStatus.READY,
                items = listOf(
                    OrderItemEntity(
                        product = product
                    )
                ),
                createdAt = LocalDateTime.now().minusMinutes(10)
            )

        )

        every { orderGateway.findAll() } returns orders

        val result = findAllOrdersUseCase.execute()

        assertEquals(2, result.size)
        assertEquals(OrderStatus.READY, result[0].status)
        assertEquals(OrderStatus.IN_PREPARATION, result[1].status)

        verify(exactly = 1) { orderGateway.findAll() }
        verify(exactly = 0) { orderGateway.findAllByStatus(any()) }
    }

    @Test
    fun `should return filtered orders by status when status is provided`() {
        val customer = CustomerEntity(
            id = UUID.randomUUID(),
            name = "John Doe",
            email = "john@example.com",
            cpf = "12345678900"
        )

        val product = ProductEntity(
            id = UUID.randomUUID(),
            name = "Product 1",
            category = CategoryEnum.BURGUER,
            price = BigDecimal("19.99"),
            amount = 10,
            description = "description"
        )

        val orders = listOf(
            OrderEntity(
                id = UUID.randomUUID(),
                customer = customer,
                status = OrderStatus.IN_PREPARATION,
                items = listOf(
                    OrderItemEntity(
                        product = product
                    )
                ),
                createdAt = LocalDateTime.now()
            )
        )

        every { orderGateway.findAllByStatus(any()) } returns orders

        val result = findAllOrdersUseCase.execute(OrderStatus.IN_PREPARATION)

        assertEquals(1, result.size)
        assertEquals(OrderStatus.IN_PREPARATION, result[0].status)

        verify(exactly = 0) { orderGateway.findAll() }
        verify(exactly = 1) { orderGateway.findAllByStatus(any()) }
    }

    @Test
    fun `should return empty list when no orders found`() {
        every { orderGateway.findAll() } returns emptyList()

        val result = findAllOrdersUseCase.execute()

        assertTrue(result.isEmpty())

        verify(exactly = 1) { orderGateway.findAll() }
        verify(exactly = 0) { orderGateway.findAllByStatus(any()) }
    }

    @Test
    fun `should return empty list when no orders found for given status`() {
        every { orderGateway.findAllByStatus(any()) } returns emptyList()

        val result = findAllOrdersUseCase.execute(OrderStatus.COMPLETED)

        assertTrue(result.isEmpty())

        verify(exactly = 0) { orderGateway.findAll() }
        verify(exactly = 1) { orderGateway.findAllByStatus(any()) }
    }
}
