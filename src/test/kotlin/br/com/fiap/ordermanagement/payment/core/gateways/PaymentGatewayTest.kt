package br.com.fiap.ordermanagement.payment.core.gateways

import br.com.fiap.ordermanagement.order.common.dtos.OrderDTO
import br.com.fiap.ordermanagement.order.core.entities.OrderEntity
import br.com.fiap.ordermanagement.order.core.entities.enums.OrderStatus
import br.com.fiap.ordermanagement.payment.common.dtos.PaymentDTO
import br.com.fiap.ordermanagement.payment.common.dtos.QRCodeDTO
import br.com.fiap.ordermanagement.payment.common.interfaces.DataSource
import br.com.fiap.ordermanagement.payment.common.interfaces.PaymentProviderClient
import br.com.fiap.ordermanagement.payment.core.entities.PaymentEntity
import br.com.fiap.ordermanagement.payment.core.entities.enums.PaymentStatus
import br.com.fiap.ordermanagement.payment.core.gateways.exception.NotFoundException
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

class PaymentGatewayTest {

    private var dataSource: DataSource = mockk(relaxed = false)
    private var paymentProviderClient: PaymentProviderClient = mockk(relaxed = false)
    private var paymentGateway: PaymentGateway = PaymentGateway(dataSource, paymentProviderClient)

    private val paymentId = UUID.randomUUID()
    private val qrCodeId = "qrcode_123"
    private val qrCodeData = "00020101021243650016COM.MERCADOLIBRE02013063638a119002405f7f12d3f3b2a8d9b0f3a3f1d8f8c1026002br491300653439528802BR5913Test Merchant6008Sao Paulo62070503***6304"
    private val testTime = LocalDateTime.now()
    private val amount = BigDecimal("99.99")

    @Test
    fun `should save payment successfully`() {
        val paymentEntity = createTestPaymentEntity()
        every { dataSource.save(any()) } just Runs

        paymentGateway.save(paymentEntity)

        verify(exactly = 1) {
            dataSource.save(any())
        }
    }

    @Test
    fun `should find payment by id`() {
        val paymentDto = createTestPaymentDto()
        every { dataSource.findById(paymentId) } returns paymentDto

        val result = paymentGateway.findById(paymentId)

        assertEquals(paymentId, result.id)
        assertEquals(PaymentStatus.PENDING, result.status)
        verify(exactly = 1) { dataSource.findById(any()) }
    }

    @Test
    fun `should throw exception when payment not found by id`() {
        every { dataSource.findById(paymentId) } returns null

        assertThrows<NotFoundException> {
            paymentGateway.findById(paymentId)
        }

        verify(exactly = 1) { dataSource.findById(any()) }
    }

    @Test
    fun `should find order by order id`() {
        val orderDto = createTestOrderDto()
        every { dataSource.findOrderByOrderId(any()) } returns orderDto

        val result = paymentGateway.findOrderByOrderId(orderDto.id!!)

        assertEquals(orderDto.id, result.id)
        assertEquals(OrderStatus.RECEIVED, result.status)
        verify(exactly = 1) { dataSource.findOrderByOrderId(any()) }
    }

    @Test
    fun `should throw exception when order not found by order id`() {
        every { dataSource.findOrderByOrderId(any()) } returns null

        assertThrows<NotFoundException> {
            paymentGateway.findOrderByOrderId(UUID.randomUUID())
        }

        verify(exactly = 1) { dataSource.findOrderByOrderId(any()) }
    }

    @Test
    fun `should generate QR code successfully`() {
        val order = createTestOrderEntity()
        val qrCodeDto = QRCodeDTO(qrCodeId, qrCodeData)

        every {
            paymentProviderClient.generateQRCode(amount, any())
        } returns qrCodeDto

        val result = paymentGateway.generateQRCode(amount, order)

        assertEquals(qrCodeId, result.externalId)
        assertEquals(qrCodeData, result.qrCode)
        assertEquals(amount, result.value)

        verify(exactly = 1) {
            paymentProviderClient.generateQRCode(any(), any())
        }
    }

    @Test
    fun `should update order`() {
        val orderId = UUID.randomUUID()
        every { dataSource.updateOrder(any(), any()) } just Runs

        paymentGateway.updateOrder(orderId, PaymentStatus.APPROVED)

        verify(exactly = 1) { dataSource.updateOrder(any(), any()) }
    }

    private fun createTestPaymentEntity(): PaymentEntity {
        return PaymentEntity(
            id = paymentId,
            order = createTestOrderEntity(),
            value = amount,
            status = PaymentStatus.PENDING,
            externalId = qrCodeId,
            qrCode = qrCodeData,
            createdAt = testTime,
            updatedAt = testTime
        )
    }

    private fun createTestPaymentDto(): PaymentDTO {
        return PaymentDTO(
            id = paymentId,
            order = createTestOrderDto(),
            status = PaymentStatus.PENDING,
            externalId = qrCodeId,
            qrCode = qrCodeData,
            createdAt = testTime,
            updatedAt = testTime
        )
    }

    private fun createTestOrderEntity(): OrderEntity {
        return OrderEntity(
            id = UUID.randomUUID(),
            customer = null,
            status = OrderStatus.RECEIVED,
            items = emptyList(),
            createdAt = testTime,
            updatedAt = testTime
        )
    }

    private fun createTestOrderDto(): OrderDTO {
        return OrderDTO(
            id = UUID.randomUUID(),
            status = OrderStatus.RECEIVED,
            items = emptyList(),
            createdAt = testTime,
            updatedAt = testTime
        )
    }
}
