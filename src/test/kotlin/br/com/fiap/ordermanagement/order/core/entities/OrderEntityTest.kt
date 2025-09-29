package br.com.fiap.ordermanagement.order.core.entities

import br.com.fiap.ordermanagement.customer.core.entities.CustomerEntity
import br.com.fiap.ordermanagement.order.core.entities.enums.OrderStatus
import br.com.fiap.ordermanagement.order.core.entities.enums.OrderStatus.CANCELED
import br.com.fiap.ordermanagement.order.core.entities.enums.OrderStatus.COMPLETED
import br.com.fiap.ordermanagement.order.core.entities.enums.OrderStatus.CREATED
import br.com.fiap.ordermanagement.order.core.entities.enums.OrderStatus.IN_PREPARATION
import br.com.fiap.ordermanagement.order.core.entities.enums.OrderStatus.OVERDUE
import br.com.fiap.ordermanagement.order.core.entities.enums.OrderStatus.PAID
import br.com.fiap.ordermanagement.order.core.entities.enums.OrderStatus.READY
import br.com.fiap.ordermanagement.order.core.entities.enums.OrderStatus.RECEIVED
import br.com.fiap.ordermanagement.order.core.gateways.exception.InvalidUpdateException
import br.com.fiap.ordermanagement.product.core.entities.ProductEntity
import br.com.fiap.ordermanagement.product.core.entities.enums.CategoryEnum
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime
import java.util.*

class OrderEntityTest {

    private val testProduct = ProductEntity(
        id = UUID.randomUUID(),
        name = "Test Product",
        category = CategoryEnum.BURGUER,
        price = 10.0.toBigDecimal(),
        amount = 5,
        description = "description"
    )

    private val testCustomer = CustomerEntity(
        id = UUID.randomUUID(),
        name = "Test Customer",
        email = "test@example.com",
        cpf = "123.456.789-00"
    )

    @Test
    fun `should create order with default values`() {
        val order = OrderEntity(customer = testCustomer)

        assertNotNull(order.id)
        assertEquals(CREATED, order.status)
        assertTrue(order.items.isEmpty())
        assertEquals(testCustomer, order.customer)
        assertNotNull(order.createdAt)
        assertNotNull(order.updatedAt)
    }

    @Test
    fun `should create order with all fields`() {
        val id = UUID.randomUUID()
        val now = LocalDateTime.now()
        val items = listOf(createTestOrderItem())

        val order = OrderEntity(
            id = id,
            status = PAID,
            items = items,
            customer = testCustomer,
            createdAt = now,
            updatedAt = now
        )

        assertEquals(id, order.id)
        assertEquals(PAID, order.status)
        assertEquals(items, order.items)
        assertEquals(testCustomer, order.customer)
        assertEquals(now, order.createdAt)
        assertEquals(now, order.updatedAt)
    }

    @Test
    fun `should validate status transition from CREATED to PAID`() {
        val order = createTestOrder(CREATED)

        assertDoesNotThrow {
            (order.validate(PAID))
        }
    }

    @Test
    fun `should validate status transition from CREATED to CANCELED`() {
        val order = createTestOrder(CREATED)

        assertDoesNotThrow {
            (order.validate(CANCELED))
        }
    }

    @Test
    fun `should validate status transition from PAID to RECEIVED`() {
        val order = createTestOrder(PAID)

        assertDoesNotThrow {
            (order.validate(RECEIVED))
        }
    }

    @Test
    fun `should validate status transition from RECEIVED to IN_PREPARATION`() {
        val order = createTestOrder(RECEIVED)

        assertDoesNotThrow {
            (order.validate(IN_PREPARATION))
        }
    }

    @Test
    fun `should validate status transition from IN_PREPARATION to READY`() {
        val order = createTestOrder(IN_PREPARATION)

        assertDoesNotThrow {
            (order.validate(READY))
        }
    }

    @Test
    fun `should validate status transition from IN_PREPARATION to OVERDUE`() {
        val order = createTestOrder(IN_PREPARATION)

        assertDoesNotThrow {
            (order.validate(OVERDUE))
        }
    }

    @Test
    fun `should validate status transition from OVERDUE to READY`() {
        val order = createTestOrder(OVERDUE)

        assertDoesNotThrow {
            (order.validate(READY))
        }
    }

    @Test
    fun `should validate status transition from READY to COMPLETED`() {
        val order = createTestOrder(READY)

        assertDoesNotThrow {
            (order.validate(COMPLETED))
        }
    }

    @Test
    fun `should throw exception for invalid status transition`() {
        val order = OrderEntity(
            status = RECEIVED,
            customer = testCustomer
        )

        assertThrows<InvalidUpdateException> {
            order.validate(READY)
        }
    }

    private fun createTestOrder(
        status: OrderStatus = CREATED,
        items: List<OrderItemEntity> = listOf(createTestOrderItem())
    ): OrderEntity {
        return OrderEntity(
            status = status,
            items = items,
            customer = testCustomer
        )
    }

    private fun createTestOrderItem(productName: String = "Test Product"): OrderItemEntity {
        val product = testProduct.copy(name = productName)
        return OrderItemEntity(product = product)
    }
}
