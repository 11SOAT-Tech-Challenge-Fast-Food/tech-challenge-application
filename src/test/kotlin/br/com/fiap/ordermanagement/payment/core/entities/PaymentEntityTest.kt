package br.com.fiap.ordermanagement.payment.core.entities

import br.com.fiap.ordermanagement.order.core.entities.OrderEntity
import br.com.fiap.ordermanagement.order.core.entities.enums.OrderStatus
import br.com.fiap.ordermanagement.payment.core.entities.enums.PaymentStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

class PaymentEntityTest {

    @Test
    fun `should create payment with default values`() {
        val payment = PaymentEntity()

        assertNotNull(payment.id)
        assertNull(payment.externalId)
        assertEquals(PaymentStatus.PENDING, payment.status)
        assertNull(payment.order)
        assertEquals(BigDecimal.ZERO, payment.value)
        assertNull(payment.qrCode)
        assertNotNull(payment.createdAt)
        assertNotNull(payment.updatedAt)
    }

    @Test
    fun `should create payment with all fields`() {
        val id = UUID.randomUUID()
        val externalId = "ext_123"
        val status = PaymentStatus.APPROVED
        val order = OrderEntity(
            id = id,
            status = OrderStatus.CREATED,
            items = emptyList(),
            customer = null,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        val value = BigDecimal("99.99")
        val qrCode = "qrcode123"
        val now = LocalDateTime.now()

        val payment = PaymentEntity(
            id = id,
            externalId = externalId,
            status = status,
            order = order,
            value = value,
            qrCode = qrCode,
            createdAt = now,
            updatedAt = now
        )

        assertEquals(id, payment.id)
        assertEquals(externalId, payment.externalId)
        assertEquals(status, payment.status)
        assertEquals(order, payment.order)
        assertEquals(value, payment.value)
        assertEquals(qrCode, payment.qrCode)
        assertEquals(now, payment.createdAt)
        assertEquals(now, payment.updatedAt)
    }
}
