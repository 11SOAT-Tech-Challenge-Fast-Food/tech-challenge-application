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
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

class FindOrderByIdUseCaseTest {

    private var orderGateway: IOrderGateway = mockk(relaxed = false)
    private var findOrderByIdUseCase: FindOrderByIdUseCase = FindOrderByIdUseCase(orderGateway)

    @Test
    fun `should find order by id successfully`() {
        val customer = CustomerEntity(
            id = UUID.randomUUID(),
            name = "John Doe",
            email = "john@example.com",
            cpf = "12345678900"
        )

        val product = ProductEntity(
            id = UUID.randomUUID(),
            name = "Test Product",
            category = CategoryEnum.BURGUER,
            price = BigDecimal("29.99"),
            amount = 10,
            description = "Test description"
        )

        val expectedOrder = OrderEntity(
            id = UUID.randomUUID(),
            customer = customer,
            status = OrderStatus.RECEIVED,
            items = listOf(
                OrderItemEntity(
                    product = product
                )
            ),
            createdAt = LocalDateTime.now()
        )

        every { orderGateway.findById(any()) } returns expectedOrder

        val result = findOrderByIdUseCase.execute(expectedOrder.id)

        assertNotNull(result)
        assertEquals(expectedOrder.id, result.id)

        verify(exactly = 1) { orderGateway.findById(any()) }
    }

    @Test
    fun `should throw exception when order not found`() {
        every { orderGateway.findById(any()) } throws NoSuchElementException("Order not found")

        assertThrows<NoSuchElementException> {
            findOrderByIdUseCase.execute(UUID.randomUUID())
        }

        verify(exactly = 1) { orderGateway.findById(any()) }
    }
}
