package br.com.fiap.ordermanagement.order.core.usecases

import br.com.fiap.ordermanagement.customer.core.entities.CustomerEntity
import br.com.fiap.ordermanagement.order.core.entities.enums.OrderStatus
import br.com.fiap.ordermanagement.order.core.gateways.IOrderGateway
import br.com.fiap.ordermanagement.product.core.entities.ProductEntity
import br.com.fiap.ordermanagement.product.core.entities.enums.CategoryEnum
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.util.*

class CreateOrderUseCaseTest {

    private var orderGateway: IOrderGateway = mockk(relaxed = false)
    private var createOrderUseCase: CreateOrderUseCase = CreateOrderUseCase(orderGateway)

    @Test
    fun `should create order successfully with customer`() {
        val customerId = UUID.randomUUID()

        val customer = CustomerEntity(
            id = customerId,
            name = "John Doe",
            email = "john.doe@example.com",
            cpf = "123.456.789-00"
        )

        val product = ProductEntity(
            id = UUID.randomUUID(),
            name = "Product 2",
            description = "Description 2",
            category = CategoryEnum.DRINK,
            price = BigDecimal.valueOf(200.0),
            amount = 3
        )

        every { orderGateway.findCustomerByCustomerId(customerId) } returns customer
        every { orderGateway.findProductById(any()) } returns product
        every { orderGateway.save(any()) } just Runs
        every { orderGateway.updateProduct(any()) } just Runs

        val result = createOrderUseCase.execute(customerId, listOf(product.id))

        assertNotNull(result.id)
        assertEquals(1, result.items.size)
        assertEquals(customer, result.customer)
        assertEquals(OrderStatus.CREATED, result.status)

        verify(exactly = 1) { orderGateway.findCustomerByCustomerId(customerId) }
        verify(exactly = 1) { orderGateway.findProductById(any()) }
        verify(exactly = 1) { orderGateway.save(any()) }
        verify(exactly = 1) { orderGateway.updateProduct(any()) }
    }

    @Test
    fun `should create order successfully without customer`() {
        val product = ProductEntity(
            id = UUID.randomUUID(),
            name = "Product 2",
            description = "Description 2",
            category = CategoryEnum.DRINK,
            price = BigDecimal.valueOf(200.0),
            amount = 3
        )

        every { orderGateway.findProductById(any()) } returns product
        every { orderGateway.save(any()) } just Runs
        every { orderGateway.updateProduct(any()) } just Runs

        val result = createOrderUseCase.execute(null, listOf(product.id))

        assertNotNull(result.id)
        assertEquals(1, result.items.size)
        assertNull(result.customer)

        verify(exactly = 0) { orderGateway.findCustomerByCustomerId(any()) }
        verify(exactly = 1) { orderGateway.findProductById(any()) }
        verify(exactly = 1) { orderGateway.save(any()) }
        verify(exactly = 1) { orderGateway.updateProduct(any()) }
    }

    @Test
    fun `should throw exception when product is out of stock`() {
        val product = ProductEntity(
            id = UUID.randomUUID(),
            name = "Out of Stock Product",
            description = "Out of stock",
            category = CategoryEnum.BURGUER,
            price = BigDecimal.valueOf(100.0),
            amount = 0
        )

        every { orderGateway.findProductById(any()) } returns product

        assertThrows<IllegalArgumentException> {
            createOrderUseCase.execute(null, listOf(product.id))
        }

        verify(exactly = 0) { orderGateway.findCustomerByCustomerId(any()) }
        verify(exactly = 1) { orderGateway.findProductById(any()) }
        verify(exactly = 0) { orderGateway.save(any()) }
        verify(exactly = 0) { orderGateway.updateProduct(any()) }
    }
}
