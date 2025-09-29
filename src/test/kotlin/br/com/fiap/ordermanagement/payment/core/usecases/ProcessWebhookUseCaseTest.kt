package br.com.fiap.ordermanagement.payment.core.usecases

import br.com.fiap.ordermanagement.order.core.entities.OrderEntity
import br.com.fiap.ordermanagement.order.core.entities.enums.OrderStatus
import br.com.fiap.ordermanagement.payment.core.entities.PaymentEntity
import br.com.fiap.ordermanagement.payment.core.entities.enums.PaymentStatus
import br.com.fiap.ordermanagement.payment.core.gateways.IPaymentGateway
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

class ProcessWebhookUseCaseTest {

    private var paymentGateway: IPaymentGateway = mockk(relaxed = false)
    private var processWebhookUseCase: ProcessWebhookUseCase = ProcessWebhookUseCase(paymentGateway)

    @Test
    fun `should update payment status when action is update and payment is approved`() {
        val paymentProviderId = "webhook_123"
        val paymentId = UUID.randomUUID()

        val providerDetails = PaymentEntity(
            id = paymentId,
            status = PaymentStatus.APPROVED
        )

        val existingPayment = PaymentEntity(
            id = paymentId,
            value = BigDecimal("100.00"),
            status = PaymentStatus.PENDING,
            qrCode = "test_qr_code",
            createdAt = LocalDateTime.now(),
            order = OrderEntity(
                id = UUID.randomUUID(),
                status = OrderStatus.CREATED,
                items = emptyList(),
                customer = null
            )
        )

        every { paymentGateway.findByPaymentProviderId(any()) } returns existingPayment
        every { paymentGateway.save(any()) } just Runs
        every { paymentGateway.updateOrder(any(), any()) } just Runs

        processWebhookUseCase.execute(paymentProviderId, PaymentStatus.APPROVED)

        verify(exactly = 1) { paymentGateway.findByPaymentProviderId(any()) }
        verify(exactly = 1) { paymentGateway.save(any()) }
        verify(exactly = 1) { paymentGateway.updateOrder(any(), any()) }
    }

    @Test
    fun `should handle exception when payment not found`() {
        val paymentProviderId = "webhook_123"

        every { paymentGateway.findByPaymentProviderId(any()) } throws NoSuchElementException("Payment not found")

        assertDoesNotThrow {
            processWebhookUseCase.execute(paymentProviderId, PaymentStatus.APPROVED)
        }

        verify(exactly = 1) { paymentGateway.findByPaymentProviderId(any()) }
        verify(exactly = 0) { paymentGateway.save(any()) }
        verify(exactly = 0) { paymentGateway.updateOrder(any(), any()) }
    }
}
