package br.com.fiap.ordermanagement.order.core.gateways

import br.com.fiap.ordermanagement.customer.common.dtos.CustomerDTO
import br.com.fiap.ordermanagement.customer.core.entities.CustomerEntity
import br.com.fiap.ordermanagement.order.common.interfaces.DataSource
import br.com.fiap.ordermanagement.order.common.mapper.OrderMapper.toDto
import br.com.fiap.ordermanagement.order.core.entities.OrderEntity
import br.com.fiap.ordermanagement.order.core.entities.OrderItemEntity
import br.com.fiap.ordermanagement.order.core.entities.enums.OrderStatus
import br.com.fiap.ordermanagement.order.core.gateways.exception.NotFoundOrderException
import br.com.fiap.ordermanagement.product.common.dtos.ProductDTO
import br.com.fiap.ordermanagement.product.core.entities.ProductEntity
import br.com.fiap.ordermanagement.product.core.entities.enums.CategoryEnum
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

class OrderGatewayTest {

    private var dataSource: DataSource = mockk(relaxed = false)
    private var orderGateway: OrderGateway = OrderGateway(dataSource)

    private val orderId = UUID.randomUUID()
    private val customerId = UUID.randomUUID()
    private val productId = UUID.randomUUID()
    private val testTime = LocalDateTime.now()

    @Test
    fun `should save order successfully`() {
        val orderEntity = createTestOrderEntity()

        every { dataSource.save(any()) } just Runs

        orderGateway.save(orderEntity)

        verify(exactly = 1) {
            dataSource.save(any())
        }
    }

    @Test
    fun `should update order successfully`() {
        val orderEntity = createTestOrderEntity().copy(
            id = orderId,
            status = OrderStatus.IN_PREPARATION
        )

        every { dataSource.update(any()) } just Runs

        orderGateway.update(orderEntity)

        verify(exactly = 1) {
            dataSource.update(any())
        }
    }

    @Test
    fun `should find order by id`() {
        val orderDto = createTestOrderEntity().toDto()

        every { dataSource.findById(orderId) } returns orderDto

        val result = orderGateway.findById(orderId)

        assertEquals(orderId, result!!.id)
        assertEquals(OrderStatus.RECEIVED, result.status)
        verify(exactly = 1) { dataSource.findById(orderId) }
    }

    @Test
    fun `should return null when order not found by id`() {
        every { dataSource.findById(orderId) } returns null

        val result = orderGateway.findById(orderId)

        assertEquals(result, null)

        verify(exactly = 1) { dataSource.findById(orderId) }
    }

    @Test
    fun `should find all orders`() {
        val orderDto = createTestOrderEntity().toDto()
        every { dataSource.findAll() } returns listOf(orderDto)

        val result = orderGateway.findAll()

        assertEquals(1, result.size)
        assertEquals(orderId, result[0].id)
        verify(exactly = 1) { dataSource.findAll() }
    }

    @Test
    fun `should find all orders by status`() {
        val orderDto = createTestOrderEntity().toDto().copy(status = OrderStatus.IN_PREPARATION)
        every { dataSource.findAllByStatus(OrderStatus.IN_PREPARATION) } returns listOf(orderDto)

        val result = orderGateway.findAllByStatus(OrderStatus.IN_PREPARATION)

        assertEquals(1, result.size)
        assertEquals(OrderStatus.IN_PREPARATION, result[0].status)
        verify(exactly = 1) { dataSource.findAllByStatus(OrderStatus.IN_PREPARATION) }
    }

    @Test
    fun `should find product by id`() {
        val productDto = ProductDTO(
            id = productId,
            name = "Test Product",
            category = CategoryEnum.DRINK,
            price = BigDecimal("10.50"),
            amount = 5,
            description = "Test description"
        )

        every { dataSource.findProductById(productId) } returns productDto

        val result = orderGateway.findProductById(productId)

        assertEquals(productId, result.id)
        assertEquals("Test Product", result.name)
        verify(exactly = 1) { dataSource.findProductById(productId) }
    }

    @Test
    fun `should throw exception when product not found`() {
        every { dataSource.findProductById(productId) } returns null

        val exception = assertThrows<NotFoundOrderException> {
            orderGateway.findProductById(productId)
        }

        assertEquals("Product not found", exception.message)
        verify(exactly = 1) { dataSource.findProductById(productId) }
    }

    @Test
    fun `should update product`() {
        val productEntity = ProductEntity(
            id = productId,
            name = "Updated Product",
            category = CategoryEnum.DRINK,
            price = BigDecimal("15.75"),
            amount = 10,
            description = "Updated description"
        )

        every { dataSource.updateProduct(any()) } just Runs

        orderGateway.updateProduct(productEntity)

        verify(exactly = 1) { dataSource.updateProduct(any()) }
    }

    @Test
    fun `should find customer by id`() {
        val customerDto = CustomerDTO(
            id = customerId,
            name = "John Doe",
            email = "john@example.com",
            cpf = "123.456.789-00"
        )

        every { dataSource.findCustomerById(customerId) } returns customerDto

        val result = orderGateway.findCustomerByCustomerId(customerId)

        assertEquals(customerId, result.id)
        assertEquals("John Doe", result.name)
        verify(exactly = 1) { dataSource.findCustomerById(customerId) }
    }

    @Test
    fun `should throw exception when customer not found`() {
        every { dataSource.findCustomerById(customerId) } returns null

        assertThrows<NotFoundOrderException> {
            orderGateway.findCustomerByCustomerId(customerId)
        }

        verify(exactly = 1) { dataSource.findCustomerById(customerId) }
    }

    private fun createTestOrderEntity(): OrderEntity {
        return OrderEntity(
            id = orderId,
            customer = CustomerEntity(
                id = customerId,
                name = "John Doe",
                email = "john@example.com",
                cpf = "123.456.789-00"
            ),
            status = OrderStatus.RECEIVED,
            items = listOf(
                OrderItemEntity(
                    product = ProductEntity(
                        id = productId,
                        name = "Test Product",
                        category = CategoryEnum.DRINK,
                        price = BigDecimal("10.50"),
                        amount = 5,
                        description = "Test description"
                    )
                )
            ),
            createdAt = testTime,
            updatedAt = testTime
        )
    }
}
