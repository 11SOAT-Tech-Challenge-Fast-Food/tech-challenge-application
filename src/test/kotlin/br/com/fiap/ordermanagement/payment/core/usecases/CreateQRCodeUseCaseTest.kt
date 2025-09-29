package br.com.fiap.ordermanagement.payment.core.usecases

import br.com.fiap.ordermanagement.order.core.entities.OrderEntity
import br.com.fiap.ordermanagement.order.core.entities.OrderItemEntity
import br.com.fiap.ordermanagement.order.core.entities.enums.OrderStatus
import br.com.fiap.ordermanagement.payment.core.entities.PaymentEntity
import br.com.fiap.ordermanagement.payment.core.entities.enums.PaymentStatus
import br.com.fiap.ordermanagement.payment.core.gateways.IPaymentGateway
import br.com.fiap.ordermanagement.product.core.entities.ProductEntity
import br.com.fiap.ordermanagement.product.core.entities.enums.CategoryEnum
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

class CreateQRCodeUseCaseTest {

    private var paymentGateway: IPaymentGateway = mockk(relaxed = false)
    private var createQRCodeUseCase: CreateQRCodeUseCase = CreateQRCodeUseCase(paymentGateway)

    @Test
    fun `should create QR code successfully`() {
        val orderId = UUID.randomUUID()

        val product = ProductEntity(
            id = UUID.randomUUID(),
            name = "Test Product",
            description = "Test Description",
            category = CategoryEnum.DRINK,
            price = BigDecimal("50.00"),
            amount = 10
        )

        val order = OrderEntity(
            id = orderId,
            status = OrderStatus.CREATED,
            items = listOf(OrderItemEntity(product = product)),
            customer = null
        )

        val expectedPayment = PaymentEntity(
            id = UUID.randomUUID(),
            order = order,
            value = BigDecimal("50.00"),
            status = PaymentStatus.PENDING,
            qrCode = "test_qr_code",
            createdAt = LocalDateTime.now()
        )

        every { paymentGateway.findOrderByOrderId(orderId) } returns order
        every { paymentGateway.generateQRCode(any(), any()) } returns expectedPayment
        every { paymentGateway.save(any()) } just Runs

        val result = createQRCodeUseCase.execute(orderId)

        assertNotNull(result)

        verify(exactly = 1) { paymentGateway.findOrderByOrderId(any()) }
        verify(exactly = 1) { paymentGateway.generateQRCode(any(), any()) }
        verify(exactly = 1) { paymentGateway.save(any()) }
    }

    @Test
    fun `should throw exception when order is not found`() {
        every { paymentGateway.findOrderByOrderId(any()) } throws NoSuchElementException("Order not found")

        assertThrows<NoSuchElementException> {
            createQRCodeUseCase.execute(UUID.randomUUID())
        }

        verify(exactly = 1) { paymentGateway.findOrderByOrderId(any()) }
        verify(exactly = 0) { paymentGateway.generateQRCode(any(), any()) }
        verify(exactly = 0) { paymentGateway.save(any()) }
    }
}
